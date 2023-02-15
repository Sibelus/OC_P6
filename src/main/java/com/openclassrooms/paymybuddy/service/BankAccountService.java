package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyBankAccountNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService implements IBankAccountService{

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private IUserService iUserService;
    Logger logger = LoggerFactory.getLogger(BankAccountService.class);


    @Override
    public void addBankAccount(BankAccount bankAccount) throws EmptyBankAccountNameException {
        if(bankAccount.getName() == null){
            logger.error("bank account name provided is null");
            throw new NullPointerException("bank account name provided is incorrect: " + bankAccount.getName());
        }
        if(bankAccount.getName().equals("")){
            logger.error("bank account name provided is empty");
            throw new EmptyBankAccountNameException("Bank account name provided is empty");
        }
        bankAccount.setUserId(iUserService.getCurrentUser().getId());
        bankAccount.setAmount(1000);
        bankAccountRepository.save(bankAccount);
        logger.debug("Save new bank account");
    }
}
