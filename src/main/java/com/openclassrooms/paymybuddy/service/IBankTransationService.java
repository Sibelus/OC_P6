package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import org.springframework.stereotype.Service;

@Service
public interface IBankTransationService {

    void bankToUser(int amount, BankAccount bank, String comment);
    void userToBank(int amount, BankAccount bank, String comment);
}
