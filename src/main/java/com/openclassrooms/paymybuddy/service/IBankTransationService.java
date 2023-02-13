package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBankTransationService {

    void bankToUser(BankTransaction bankTransaction);
    void userToBank(BankTransaction bankTransaction);
    List<BankTransaction> getBankTransactions(int userId);
}
