package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.InAppTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.InAppTransactionRepository;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class InAppTransactionServiceTest {

    @MockBean
    private IFeeService iFeeService;
    @Autowired
    private InAppTransactionRepository inAppTransactionRepository;
    @Autowired
    private IInappTransactionService iInappTransactionService;


    @Test
    public void testSendMoneyToFriend() throws InsufficientAmountException, NegativeAmountException {
        //GIVEN
        InAppTransaction inAppTransaction = new InAppTransaction();
        User user = new User();
        user.setId(3);
        user.setAmount(100);
        User receiver = new User();
        receiver.setId(4);
        receiver.setAmount(0);

        inAppTransaction.setSender(user);
        inAppTransaction.setReceiver(receiver);
        inAppTransaction.setAmount(10);

        when(iFeeService.calculateFee(10)).thenReturn(Float.valueOf(1));

        //WHEN
        iInappTransactionService.sendMoneyToFriend(inAppTransaction);

        //THEN
        Assertions.assertEquals(90, user.getAmount());
        Assertions.assertEquals(10, receiver.getAmount());
    }

    @Test
    public void testSendMoneyToFriend_WithInsufficientAmount() throws InsufficientAmountException, NegativeAmountException {
        //GIVEN
        InAppTransaction inAppTransaction = new InAppTransaction();
        User user = new User();
        user.setId(3);
        user.setAmount(0);
        User receiver = new User();
        receiver.setId(4);

        inAppTransaction.setSender(user);
        inAppTransaction.setReceiver(receiver);
        inAppTransaction.setAmount(10);

        //THEN
        Assertions.assertThrows(InsufficientAmountException.class, ()-> iInappTransactionService.sendMoneyToFriend(inAppTransaction));
    }

    @Test
    public void testSendMoneyToFriend_WithNegativeAmount() throws InsufficientAmountException, NegativeAmountException {
        //GIVEN
        InAppTransaction inAppTransaction = new InAppTransaction();
        User user = new User();
        user.setId(3);
        user.setAmount(0);
        User receiver = new User();
        receiver.setId(4);

        inAppTransaction.setSender(user);
        inAppTransaction.setReceiver(receiver);
        inAppTransaction.setAmount(-10);

        //THEN
        Assertions.assertThrows(NegativeAmountException.class, ()-> iInappTransactionService.sendMoneyToFriend(inAppTransaction));
    }
}
