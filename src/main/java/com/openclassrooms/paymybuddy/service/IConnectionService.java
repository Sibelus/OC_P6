package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.service.exceptions.AllReadyFrienWithException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.FriendWithMyselfException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IConnectionService {
    void addFriendship(String email) throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException;
    List<Connection> getFriendshipList(int currentUserId);
}
