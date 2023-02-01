package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IBankAccountService {
    Iterable<BankAccount> getBankAccounts();
    Optional<BankAccount> getBankAccountById(Integer id);
}
