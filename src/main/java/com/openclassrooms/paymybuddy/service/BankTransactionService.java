package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
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


    @Override
    public void bankToUser(BankTransaction bankTransaction) {
        //Update user inApp credit
        User currentUser = bankTransaction.getUser();
        currentUser.setAmount(currentUser.getAmount() + bankTransaction.getAmount());
        //Update bank account credit
        BankAccount bankAccount = bankTransaction.getBankAccount();
        bankAccount.setAmount(bankAccount.getAmount() - bankTransaction.getAmount());
        //Calculate fee for the transaction
        bankTransaction.setFee(iFeeService.calculateFee(bankTransaction.getAmount()));

        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public void userToBank(BankTransaction bankTransaction) {
        //Update user inApp credit
        User currentUser = bankTransaction.getUser();
        currentUser.setAmount(currentUser.getAmount() - bankTransaction.getAmount());
        //Update bank account credit
        BankAccount bankAccount = bankTransaction.getBankAccount();
        bankAccount.setAmount(bankAccount.getAmount() + bankTransaction.getAmount());
        //Calculate fee for the transaction
        bankTransaction.setFee(iFeeService.calculateFee(bankTransaction.getAmount()));
        //Set amount of transaction to negative value -> help visualize exiting money from application
        bankTransaction.setAmount(bankTransaction.getAmount()*(-1));

        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public List<BankTransaction> getBankTransactions(int userId) {
        List<BankTransaction> bankTransactions = bankTransactionRepository.findByUserId(userId);
        return bankTransactions;
    }
}
