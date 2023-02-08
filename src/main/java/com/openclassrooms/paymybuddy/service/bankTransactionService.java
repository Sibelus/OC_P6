package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class bankTransactionService implements IBankTransationService{

    @Autowired
    IUserService iUserService;
    @Autowired
    IFeeService iFeeService;

    @Override
    public void bankToUser(int amount, BankAccount bank, String comment) {
        BankTransaction bankTransaction = new BankTransaction();
        User currentUser = iUserService.getCurrentUser();
        currentUser.setAmount(currentUser.getAmount() + amount);
        bank.setAmount(bank.getAmount() - amount);

        bankTransaction.setBank(bank);
        bankTransaction.setUser(currentUser);
        bankTransaction.setAmount(amount);
        bankTransaction.setComment(comment);
        bankTransaction.setFee(iFeeService.calculateFee(amount));
    }

    @Override
    public void userToBank(int amount, BankAccount bank, String comment) {
        BankTransaction bankTransaction = new BankTransaction();
        User currentUser = iUserService.getCurrentUser();
        currentUser.setAmount(currentUser.getAmount() - amount);
        bank.setAmount(bank.getAmount() + amount);

        bankTransaction.setBank(bank);
        bankTransaction.setUser(currentUser);
        bankTransaction.setAmount(amount);
        bankTransaction.setComment(comment);
        bankTransaction.setFee(iFeeService.calculateFee(amount));
    }
}
