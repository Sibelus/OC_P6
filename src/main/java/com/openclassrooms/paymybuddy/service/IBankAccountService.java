package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyBankAccountNameException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IBankAccountService {
    void addBankAccount(BankAccount bankAccount) throws EmptyBankAccountNameException;
}
