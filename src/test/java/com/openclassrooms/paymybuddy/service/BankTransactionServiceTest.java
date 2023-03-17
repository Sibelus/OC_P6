package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankTransactionRepository;
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
public class BankTransactionServiceTest {

    @MockBean
    private IFeeService iFeeService;
    @Autowired
    private IBankTransationService iBankTransationService;
    @Autowired
    private BankTransactionRepository bankTransactionRepository;


    /* ------------ bankToUser() ------------*/
    @Test
    public void testBankToUser() throws InsufficientAmountException, NegativeAmountException  {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        User user = new User();
        user.setId(1);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);
        bankAccount.setAmount(1000);

        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(10);
        when(iFeeService.calculateFee(bankTransaction.getAmount())).thenReturn((float) 1.2);

        //WHEN
        iBankTransationService.bankToUser(bankTransaction);

        //THEN
        Assertions.assertEquals(10, user.getAmount());
        Assertions.assertEquals(990, bankAccount.getAmount());
    }

    @Test
    public void testBankToUser_WithInsufficientAmount() throws InsufficientAmountException, NegativeAmountException  {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        User user = new User();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAmount(1000);

        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(1001);

        //THEN
        Assertions.assertThrows(InsufficientAmountException.class, ()-> iBankTransationService.bankToUser(bankTransaction));
    }

    @Test
    public void testBankToUser_WithNegativeAmount() throws InsufficientAmountException, NegativeAmountException  {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        User user = new User();
        BankAccount bankAccount = new BankAccount();

        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(-10);

        //THEN
        Assertions.assertThrows(NegativeAmountException.class, ()-> iBankTransationService.bankToUser(bankTransaction));
    }


    /* ------------ userToBank() ------------*/
    @Test
    public void testUserToBank() throws InsufficientAmountException, NegativeAmountException {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        User user = new User();
        user.setId(1);
        user.setAmount(1000);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1);

        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(10);
        when(iFeeService.calculateFee(bankTransaction.getAmount())).thenReturn((float) 1.2);

        //WHEN
        iBankTransationService.userToBank(bankTransaction);

        //THEN
        Assertions.assertEquals(990, user.getAmount());
        Assertions.assertEquals(10, bankAccount.getAmount());
    }

    @Test
    public void testUserToBank_WithInsufficientAmount() throws InsufficientAmountException, NegativeAmountException  {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        User user = new User();
        user.setAmount(1000);
        BankAccount bankAccount = new BankAccount();

        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(1001);

        //THEN
        Assertions.assertThrows(InsufficientAmountException.class, ()-> iBankTransationService.userToBank(bankTransaction));
    }

    @Test
    public void testUserToBank_WithNegativeAmount() throws InsufficientAmountException, NegativeAmountException  {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        User user = new User();
        BankAccount bankAccount = new BankAccount();

        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(-10);

        //THEN
        Assertions.assertThrows(NegativeAmountException.class, ()-> iBankTransationService.userToBank(bankTransaction));
    }
}
