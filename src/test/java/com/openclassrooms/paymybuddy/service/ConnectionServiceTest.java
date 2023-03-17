package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.paymybuddy.service.exceptions.AllReadyFrienWithException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.FriendWithMyselfException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class ConnectionServiceTest {

    @MockBean
    private IUserService iUserService;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private IConnectionService iConnectionService;


    @Test
    public void testAddFrienship() throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException {
        //GIVEN
        User user = new User();
        user.setId(4);
        user.setEmail("user@mail.com");
        User friend = new User();
        friend.setId(1);

        when(iUserService.getCurrentUser()).thenReturn(user);
        when(iUserService.findByEmail("friend@mail.com")).thenReturn(Optional.of(friend));

        //WHEN
        iConnectionService.addFriendship("friend@mail.com");

        //THEN
        Assertions.assertTrue(connectionRepository.existsByUserIdAndFriendId(user.getId(), friend.getId()));
    }

    @Test
    public void testAddFriendship_WhithNullEmailProvided() throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException {
        //GIVEN
        User user = new User();
        String email = null;

        when(iUserService.getCurrentUser()).thenReturn(user);

        //THEN
        Assertions.assertThrows(NullPointerException.class, ()-> iConnectionService.addFriendship(email));
    }

    @Test
    public void testAddFriendship_WhithEmptyEmailProvided() throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException {
        //GIVEN
        User user = new User();
        String email = "";

        when(iUserService.getCurrentUser()).thenReturn(user);

        //THEN
        Assertions.assertThrows(EmptyEmailException.class, ()-> iConnectionService.addFriendship(email));
    }

    @Test
    public void testAddFriendship_WhithMySelf() throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException {
        //GIVEN
        Connection connection = new Connection();
        User user = new User();
        user.setEmail("user@mail.com");

        when(iUserService.getCurrentUser()).thenReturn(user);

        //THEN
        Assertions.assertThrows(FriendWithMyselfException.class, ()-> iConnectionService.addFriendship("user@mail.com"));
    }

    @Test
    public void testAddFriendship_WhithAnAllReadyExistingFriend() throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException {
        //GIVEN
        Connection connection = new Connection();
        User user = new User();
        user.setId(4);
        user.setEmail("user@mail.com");
        User friend = new User();
        friend.setId(2);
        friend.setEmail("friend@mail.com");

        connection.setUser(user);
        connection.setFriend(friend);
        connectionRepository.save(connection);

        when(iUserService.getCurrentUser()).thenReturn(user);
        when(iUserService.findByEmail("friend@mail.com")).thenReturn(Optional.of(friend));

        //THEN
        Assertions.assertThrows(AllReadyFrienWithException.class, ()-> iConnectionService.addFriendship("friend@mail.com"));
    }
}
