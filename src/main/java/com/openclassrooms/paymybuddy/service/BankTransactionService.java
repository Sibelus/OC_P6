package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User currentUser = bankTransaction.getUser();
        currentUser.setAmount(currentUser.getAmount() + bankTransaction.getAmount());
        BankAccount bankAccount = bankTransaction.getBankAccount();
        bankAccount.setAmount(bankAccount.getAmount() - bankTransaction.getAmount());

        bankTransactionRepository.save(bankTransaction);
    }

    @Override
    public void userToBank(BankTransaction bankTransaction) {
        User currentUser = bankTransaction.getUser();
        currentUser.setAmount(currentUser.getAmount() - bankTransaction.getAmount());
        BankAccount bankAccount = bankTransaction.getBankAccount();
        bankAccount.setAmount(bankAccount.getAmount() + bankTransaction.getAmount());

        bankTransactionRepository.save(bankTransaction);
    }
}
