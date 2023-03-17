package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.BankAccountRepository;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyBankAccountNameException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class BankAccountServiceTest {

    @MockBean
    private IUserService iUserService;
    @Autowired
    private IBankAccountService iBankAccountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Test
    public void testAddBankAccount() throws EmptyBankAccountNameException {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        User user = new User();
        user.setId(1);
        bankAccount.setName("New bank");
        when(iUserService.getCurrentUser()).thenReturn(user);

        //WHEN
        iBankAccountService.addBankAccount(bankAccount);

        //THEN
        Assertions.assertTrue(bankAccountRepository.existsByUserIdAndName(1, "New bank"));
    }

    @Test
    public void testAddBankAccount_WithNullName() throws EmptyBankAccountNameException {
        //GIVEN
        BankAccount bankAccount = new BankAccount();

        //THEN
        Assertions.assertThrows(NullPointerException.class, ()-> iBankAccountService.addBankAccount(bankAccount));
    }

    @Test
    public void testAddBankAccount_WithEmptyName() throws EmptyBankAccountNameException {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName("");

        //THEN
        Assertions.assertThrows(EmptyBankAccountNameException.class, ()-> iBankAccountService.addBankAccount(bankAccount));
    }
}
