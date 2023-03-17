package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyPasswordException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserRepository userRepository;


    /* ------------ findByEmail() ------------*/
    @Test
    public void testFindByEmail() {
        //GIVEN
        String email = "mtampion@mail.fr";

        //WHEN
        Optional<User> user = iUserService.findByEmail(email);

        //THEN
        Assertions.assertEquals("Mocktar", user.get().getFirstname());
    }


    /* ------------ addUser() ------------*/
    @Test
    public void testAddUser() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("faignement@mail.com");
        user.setPassword("123");

        //WHEN
        iUserService.addUser(user);
        User newUser = iUserService.findByEmail("faignement@mail.com").get();

        //THEN
        Assertions.assertEquals("Florence", newUser.getFirstname());
    }

    @Test
    public void testAddUser_WithEmptyFirstname() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("");

        //THEN
        Assertions.assertThrows(EmptyFirstnameException.class, ()-> iUserService.addUser(user));
    }

    @Test
    public void testAddUser_WithEmptyLastname() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("");

        //THEN
        Assertions.assertThrows(EmptyLastnameException.class, ()-> iUserService.addUser(user));
    }

    @Test
    public void testAddUser_WithEmptyEmail() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("");

        //THEN
        Assertions.assertThrows(EmptyEmailException.class, ()-> iUserService.addUser(user));
    }

    @Test
    public void testAddUser_WithEmptyPassword() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("faignement@mail.com");
        user.setPassword("");

        //THEN
        Assertions.assertThrows(EmptyPasswordException.class, ()-> iUserService.addUser(user));
    }


    /* ------------ addOauthUser() ------------*/
    @Test
    public void testAddOauthUser() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("faignement@mail.com");

        //WHEN
        iUserService.addOauthUser(user);
        User newUser = iUserService.findByEmail("faignement@mail.com").get();

        //THEN
        Assertions.assertEquals("Florence", newUser.getFirstname());
    }

    @Test
    public void testAddOauthUser_WithEmptyFirstname() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("");

        //THEN
        Assertions.assertThrows(EmptyFirstnameException.class, ()-> iUserService.addOauthUser(user));
    }

    @Test
    public void testAddOauthUser_WithEmptyLastname() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("");

        //THEN
        Assertions.assertThrows(EmptyLastnameException.class, ()-> iUserService.addOauthUser(user));
    }

    @Test
    public void testAddOauthUser_WithEmptyEmail() throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("");

        //THEN
        Assertions.assertThrows(EmptyEmailException.class, ()-> iUserService.addOauthUser(user));
    }


    /* ------------ updateUser() ------------*/
    @Test
    public void testUpdateUser_WithEmptyFirstname() throws EmptyFirstnameException, EmptyLastnameException, EmptyEmailException, EmptyPasswordException {
        //GIVEN
        User user = new User();
        user.setFirstname("");

        //THEN
        Assertions.assertThrows(EmptyFirstnameException.class, ()-> iUserService.updateUser(user));
    }

    @Test
    public void testUpdateUser_WithEmptyLastname() throws EmptyFirstnameException, EmptyLastnameException, EmptyEmailException, EmptyPasswordException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("");

        //THEN
        Assertions.assertThrows(EmptyLastnameException.class, ()-> iUserService.updateUser(user));
    }

    @Test
    public void testUpdateUser_WithEmptyEmail() throws EmptyFirstnameException, EmptyLastnameException, EmptyEmailException, EmptyPasswordException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("");

        //THEN
        Assertions.assertThrows(EmptyEmailException.class, ()-> iUserService.updateUser(user));
    }

    @Test
    public void testUpdateUser_WithEmptyPassword() throws EmptyFirstnameException, EmptyLastnameException, EmptyEmailException, EmptyPasswordException {
        //GIVEN
        User user = new User();
        user.setFirstname("Florence");
        user.setLastname("Aignement");
        user.setEmail("faignement@mail.com");
        user.setPassword("");

        //THEN
        Assertions.assertThrows(EmptyPasswordException.class, ()-> iUserService.updateUser(user));
    }
}
