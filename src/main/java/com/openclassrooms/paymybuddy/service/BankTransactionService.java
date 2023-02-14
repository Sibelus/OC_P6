package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankTransactionService implements IBankTransationService{

    @Autowired
    BankTransactionRepository bankTransactionRepository;
    @Autowired
    IUserService iUserService;
    @Autowired
    IFeeService iFeeService;


    Logger logger = LoggerFactory.getLogger(BankTransactionService.class);

    @Override
    public void bankToUser(BankTransaction bankTransaction) {

        User currentUser = bankTransaction.getUser();
        BankAccount bankAccount = bankTransaction.getBankAccount();
        int amount = bankTransaction.getAmount();

        if(bankAccount.getAmount() < amount){
            logger.error("{} try to send {} from his bank {}, but there is not enough money on his bank account", currentUser.getFirstname(),bankTransaction.getAmount(), bankAccount);
            throw new IllegalArgumentException("insufficient amount: you only have " + bankAccount.getAmount() + " on your bank account");
        }

        currentUser.setAmount(currentUser.getAmount() + bankTransaction.getAmount());
        bankAccount.setAmount(bankAccount.getAmount() - bankTransaction.getAmount());
        bankTransaction.setFee(iFeeService.calculateFee(bankTransaction.getAmount()));
        logger.debug("{} send {} from his bank {}", currentUser.getFirstname(),bankTransaction.getAmount(), bankAccount);

        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public void userToBank(BankTransaction bankTransaction) {
        User currentUser = bankTransaction.getUser();
        BankAccount bankAccount = bankTransaction.getBankAccount();
        int amount = bankTransaction.getAmount();

        if(currentUser.getAmount() < amount){
            logger.error("{} try to send {} to his bank {}, but there is not enough money on his account", currentUser.getFirstname(),bankTransaction.getAmount(), bankAccount.getName());
            throw new IllegalArgumentException("insufficient amount: you only have " + currentUser.getAmount() + " on your account");
        }

        currentUser.setAmount(currentUser.getAmount() - bankTransaction.getAmount());
        bankAccount.setAmount(bankAccount.getAmount() + bankTransaction.getAmount());
        bankTransaction.setFee(iFeeService.calculateFee(bankTransaction.getAmount()));
        //Set amount of transaction to negative value -> help visualize exiting money from application
        bankTransaction.setAmount(bankTransaction.getAmount()*(-1));
        logger.debug("{} send {} to his bank {}", currentUser.getFirstname(),bankTransaction.getAmount(), bankAccount);

        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public List<BankTransaction> getBankTransactions(int userId) {
        List<BankTransaction> bankTransactions = bankTransactionRepository.findByUserId(userId);
        return bankTransactions;
    }
}
