package com.openclassrooms.paymybuddy.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ModelTest {

    @Test
    public void testBankAccount_SetAndGetAllInfos() {
        //GIVEN
        BankAccount bankAccount = new BankAccount();
        int bankId = 1;
        int userId = 1;
        String bankName = "Crédit mutuelle";
        int amount = 10;
        List<BankTransaction> bankTransactions = new ArrayList<>();
        String toString = "BankAccount{" +
                "id=" + bankId +
                ", userId=" + userId +
                ", name='" + bankName + '\'' +
                ", amount=" + amount +
                '}';

        //WHEN
        bankAccount.setId(bankId);
        bankAccount.setUserId(userId);
        bankAccount.setName(bankName);
        bankAccount.setAmount(amount);
        bankAccount.setBankTransactions(bankTransactions);

        //THEN
        Assertions.assertEquals(bankAccount.getId(), bankId);
        Assertions.assertEquals(bankAccount.getUserId(), userId);
        Assertions.assertEquals(bankAccount.getName(), bankName);
        Assertions.assertEquals(bankAccount.getAmount(), amount);
        Assertions.assertEquals(bankAccount.getBankTransactions(), bankTransactions);
        Assertions.assertEquals(bankAccount.toString(), toString);
    }

    @Test
    public void testBankTransaction_SetAndGetAllInfos() {
        //GIVEN
        BankTransaction bankTransaction = new BankTransaction();
        int bankTransactionId = 1;
        User user = new User();
        BankAccount bankAccount = new BankAccount();
        int amount = 10;
        String comment = "Road trip";
        float fee = 1;
        String toString = "BankTransaction{" +
                "id=" + bankTransactionId +
                ", user=" + user +
                ", bankAccount=" + bankAccount +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", fee=" + fee +
                '}';

        //WHEN
        bankTransaction.setId(bankTransactionId);
        bankTransaction.setUser(user);
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setAmount(amount);
        bankTransaction.setComment(comment);
        bankTransaction.setFee(fee);

        //THEN
        Assertions.assertEquals(bankTransaction.getId(), bankTransactionId);
        Assertions.assertEquals(bankTransaction.getUser(), user);
        Assertions.assertEquals(bankTransaction.getBankAccount(), bankAccount);
        Assertions.assertEquals(bankTransaction.getAmount(), amount);
        Assertions.assertEquals(bankTransaction.getComment(), comment);
        Assertions.assertEquals(bankTransaction.getFee(), fee);
        Assertions.assertEquals(bankTransaction.toString(), toString);

    }

    @Test
    public void testConnection_SetAndGetAllInfos() {
        //GIVEN
        Connection connection = new Connection();
        int id = 1;
        User user = new User();
        User friend = new User();
        String toString = "Connection{" +
                "id=" + id +
                ", user=" + user +
                ", friend=" + friend +
                '}';

        //WHEN
        connection.setConnectionId(id);
        connection.setUser(user);
        connection.setFriend(friend);

        //THEN
        Assertions.assertEquals(connection.getConnectionId(), id);
        Assertions.assertEquals(connection.getUser(), user);
        Assertions.assertEquals(connection.getFriend(), friend);
        Assertions.assertEquals(connection.toString(), toString);
    }

    @Test
    public void testInAppTransaction_SetAndGetAllInfos() {
        //GIVEN
        InAppTransaction inAppTransaction = new InAppTransaction();
        int id = 1;
        User sender = new User();
        User receiver = new User();
        int amount = 10;
        String comment = "Road trip";
        float fee = 1;
        String toString = "InAppTransaction{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", amount=" + amount +
                ", comment='" + comment + '\'' +
                ", fee=" + fee +
                '}';

        //WHEN
        inAppTransaction.setId(id);
        inAppTransaction.setSender(sender);
        inAppTransaction.setReceiver(receiver);
        inAppTransaction.setAmount(amount);
        inAppTransaction.setComment(comment);
        inAppTransaction.setFee(fee);

        //THEN
        Assertions.assertEquals(inAppTransaction.getId(), id);
        Assertions.assertEquals(inAppTransaction.getSender(), sender);
        Assertions.assertEquals(inAppTransaction.getReceiver(), receiver);
        Assertions.assertEquals(inAppTransaction.getAmount(), amount);
        Assertions.assertEquals(inAppTransaction.getComment(), comment);
        Assertions.assertEquals(inAppTransaction.getFee(), fee);
        Assertions.assertEquals(inAppTransaction.toString(), toString);
    }

    @Test
    public void testUser_SetAndGetAllInfos() {
        //GIVEN
        User user = new User();
        int id = 1;
        String email = "sbihaille@mail.com";
        String password = "p455w0rd";
        String firstname = "Steff";
        String lastname = "Bihaille";
        int amount = 100;
        List<BankAccount> bankAccountList = new ArrayList<>();
        List<BankTransaction> bankTransactions = new ArrayList<>();
        List<Connection> friendsList = new ArrayList<>();
        List<InAppTransaction> inAppTransactions = new ArrayList<>();
        String toString = "User{" +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", amount=" + amount +
                '}';

        //WHEN
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAmount(amount);
        user.setBankAccountList(bankAccountList);
        user.setBankTransactions(bankTransactions);
        user.setFriendsList(friendsList);
        user.setInAppTransactions(inAppTransactions);

        //THEN
        Assertions.assertEquals(user.getId(), id);
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertEquals(user.getPassword(), password);
        Assertions.assertEquals(user.getFirstname(), firstname);
        Assertions.assertEquals(user.getLastname(), lastname);
        Assertions.assertEquals(user.getAmount(), amount);
        Assertions.assertEquals(user.getBankAccountList(), bankAccountList);
        Assertions.assertEquals(user.getBankTransactions(), bankTransactions);
        Assertions.assertEquals(user.getFriendsList(), friendsList);
        Assertions.assertEquals(user.getInAppTransactions(), inAppTransactions);
        Assertions.assertEquals(user.toString(), toString);
    }
}
