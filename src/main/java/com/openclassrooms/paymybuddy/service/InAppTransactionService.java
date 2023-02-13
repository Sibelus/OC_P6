package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.InAppTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InAppTransactionService implements IInappTransactionService{

    @Autowired
    IUserService iUserService;
    @Autowired
    IFeeService iFeeService;
    @Autowired
    InAppTransactionRepository inAppTransactionRepository;

    @Override
    public void sendMoneyToFriend(InAppTransaction inAppTransaction) {
        User currentUser = inAppTransaction.getSender();
        User receiver = inAppTransaction.getReceiver();
        int amount = inAppTransaction.getAmount();

        currentUser.setAmount(currentUser.getAmount() - amount);
        receiver.setAmount(receiver.getAmount() + amount);
        inAppTransaction.setFee(iFeeService.calculateFee(amount));

        inAppTransactionRepository.save(inAppTransaction);
    }

    /*
    @Override
    public void sendMoneyToFriend(int receiverId, int amount, String comment) {
        InAppTransaction inAppTransaction = new InAppTransaction();
        User currentUser = iUserService.getCurrentUser();
        Optional<User> receiver = iUserService.getUserById(receiverId);

        currentUser.setAmount(currentUser.getAmount() - amount);
        receiver.get().setAmount(receiver.get().getAmount() + amount);

        inAppTransaction.setSenderId(currentUser.getId());
        inAppTransaction.setReceiverId(receiverId);
        inAppTransaction.setAmount(amount);
        inAppTransaction.setComment(comment);
        inAppTransaction.setFee(iFeeService.calculateFee(amount));
        inAppTransactionRepository.save(inAppTransaction);
    }*/
}
