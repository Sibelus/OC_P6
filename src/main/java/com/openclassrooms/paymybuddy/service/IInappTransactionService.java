package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import org.springframework.stereotype.Service;

@Service
public interface IInappTransactionService {
    //void sendMoneyToFriend(int receiverId, int amount, String comment);
    void sendMoneyToFriend(InAppTransaction inAppTransaction);
}
