package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBankTransationService {

    void bankToUser(BankTransaction bankTransaction) throws InsufficientAmountException, NegativeAmountException;
    void userToBank(BankTransaction bankTransaction) throws InsufficientAmountException, NegativeAmountException;
    List<BankTransaction> getBankTransactions(int userId);
}
