package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.springframework.stereotype.Service;

@Service
public interface IInappTransactionService {
    //void sendMoneyToFriend(int receiverId, int amount, String comment);
    void sendMoneyToFriend(InAppTransaction inAppTransaction) throws InsufficientAmountException, NegativeAmountException;
}
