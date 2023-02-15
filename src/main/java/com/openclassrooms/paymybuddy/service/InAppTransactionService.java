package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.InAppTransactionRepository;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InAppTransactionService implements IInappTransactionService{

    @Autowired
    private IFeeService iFeeService;
    @Autowired
    private InAppTransactionRepository inAppTransactionRepository;
    Logger logger = LoggerFactory.getLogger(InAppTransactionService.class);


    @Override
    public void sendMoneyToFriend(InAppTransaction inAppTransaction) throws InsufficientAmountException, NegativeAmountException {
        User currentUser = inAppTransaction.getSender();
        User receiver = inAppTransaction.getReceiver();
        int amount = inAppTransaction.getAmount();

        if(currentUser.getAmount() < amount){
            logger.error("insufficient amount, {} try to send {} and have only {}", currentUser.getFirstname(), amount, currentUser.getAmount());
            throw new InsufficientAmountException("insufficient amount: you only have " + currentUser.getAmount());
        }
        if(inAppTransaction.getAmount() < 0){
            logger.error("{} try to send negative amount: {} to his friend {}", currentUser.getFirstname(), amount, inAppTransaction.getReceiver());
            throw new NegativeAmountException("You can't send negative amount: " + inAppTransaction.getAmount());
        }

        currentUser.setAmount(currentUser.getAmount() - amount);
        receiver.setAmount(receiver.getAmount() + amount);
        inAppTransaction.setFee(iFeeService.calculateFee(amount));

        inAppTransactionRepository.save(inAppTransaction);
        logger.debug("{} send {} to his friend {}", currentUser.getFirstname(),inAppTransaction.getAmount(), receiver.getFirstname());
    }
}
