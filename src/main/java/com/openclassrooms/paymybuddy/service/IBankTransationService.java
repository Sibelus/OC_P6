package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import org.springframework.stereotype.Service;

@Service
public interface IBankTransationService {

    void bankToUser(BankTransaction bankTransaction);
    void userToBank(BankTransaction bankTransaction);
}
