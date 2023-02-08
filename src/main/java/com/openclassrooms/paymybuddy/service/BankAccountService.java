package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService implements IBankAccountService{
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    IUserService iUserService;

    @Override
    public Iterable<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public Optional<BankAccount> getBankAccountById(Integer id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public void addBankAccount(BankAccount bankAccount) {
        bankAccount.setUserId(iUserService.getCurrentUser().getId());
        bankAccount.setAmount(1000);
        bankAccountRepository.save(bankAccount);
    }
}
