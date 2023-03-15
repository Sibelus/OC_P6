package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.InAppTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BankTransactionService implements IBankTransationService{

    @Autowired
    private BankTransactionRepository bankTransactionRepository;
    @Autowired
    private IFeeService iFeeService;
    Logger logger = LoggerFactory.getLogger(BankTransactionService.class);


    @Override
    public void bankToUser(BankTransaction bankTransaction) throws InsufficientAmountException, NegativeAmountException {

        User currentUser = bankTransaction.getUser();
        BankAccount bankAccount = bankTransaction.getBankAccount();
        int amount = bankTransaction.getAmount();

        if(bankAccount.getAmount() < amount){
            logger.error("{} try to send {} from his bank {}, but there is not enough money on his bank account", currentUser.getFirstname(), amount, bankAccount);
            throw new InsufficientAmountException("insufficient amount: you only have " + bankAccount.getAmount() + " on your bank account");
        }
        if(bankTransaction.getAmount() < 0){
            logger.error("{} try to send negative amount: {} from his bank account {}", currentUser.getFirstname(), amount, bankTransaction.getBankAccount().getName());
            throw new NegativeAmountException("You can't send negative amount: " + bankTransaction.getAmount());
        }

        currentUser.setAmount(currentUser.getAmount() + bankTransaction.getAmount());
        bankAccount.setAmount(bankAccount.getAmount() - bankTransaction.getAmount());
        bankTransaction.setFee(iFeeService.calculateFee(bankTransaction.getAmount()));

        bankTransactionRepository.save(bankTransaction);
        logger.debug("{} send {} from his bank {}", currentUser.getFirstname(),bankTransaction.getAmount(), bankAccount);
    }

    @Override
    public void userToBank(BankTransaction bankTransaction) throws InsufficientAmountException, NegativeAmountException {
        User currentUser = bankTransaction.getUser();
        BankAccount bankAccount = bankTransaction.getBankAccount();
        int amount = bankTransaction.getAmount();

        if(currentUser.getAmount() < amount){
            logger.error("{} try to send {} to his bank {}, but there is not enough money on his account", currentUser.getFirstname(), amount, bankAccount.getName());
            throw new InsufficientAmountException("insufficient amount: you only have " + currentUser.getAmount() + " on your account");
        }
        if(bankTransaction.getAmount() < 0){
            logger.error("{} try to send negative amount: {} to his bank account {}", currentUser.getFirstname(), amount, bankTransaction.getBankAccount().getName());
            throw new NegativeAmountException("You can't send negative amount: " + bankTransaction.getAmount());
        }

        currentUser.setAmount(currentUser.getAmount() - bankTransaction.getAmount());
        bankAccount.setAmount(bankAccount.getAmount() + bankTransaction.getAmount());
        bankTransaction.setFee(iFeeService.calculateFee(bankTransaction.getAmount()));
        //Set amount of transaction to negative value -> help visualize exiting money from application
        bankTransaction.setAmount(bankTransaction.getAmount()*(-1));

        bankTransactionRepository.save(bankTransaction);
        logger.debug("{} send {} to his bank {}", currentUser.getFirstname(),bankTransaction.getAmount(), bankAccount);
    }

    @Override
    public List<BankTransaction> getBankTransactions(int userId) {
        List<BankTransaction> bankTransactions = bankTransactionRepository.findByUserId(userId);
        return bankTransactions;
    }

    @Override
    public Page<BankTransaction> findPaginatedBank(Pageable pageable, List<BankTransaction> bankTransactions) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<BankTransaction> list;

        if (bankTransactions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, bankTransactions.size());
            list = bankTransactions.subList(startItem, toIndex);
        }

        Page<BankTransaction> bankTransactionPage
                = new PageImpl<BankTransaction>(list, PageRequest.of(currentPage, pageSize), bankTransactions.size());

        return bankTransactionPage;
    }
}
