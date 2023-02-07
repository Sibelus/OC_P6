package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IConnectionService {
    void addFriendship(String email);
    List<Connection> getFriendshipList(int currentUserId);
    Connection getFriend();
}
