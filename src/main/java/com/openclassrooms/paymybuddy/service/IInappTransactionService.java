package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IInappTransactionService {
    //void sendMoneyToFriend(int receiverId, int amount, String comment);
    void sendMoneyToFriend(InAppTransaction inAppTransaction) throws InsufficientAmountException, NegativeAmountException;
    Page<InAppTransaction> findPaginatedInApp(Pageable pageable, List<InAppTransaction> inAppTransactions);
}
