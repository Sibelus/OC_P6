package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService implements IConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private IUserService iUserService;


    @Override
    public void addFriendship(String email) {
        Connection friendship = new Connection();
        User currentUser = iUserService.getCurrentUser();
        User friend = iUserService.findByEmail(email).get();

        friendship.setUser(currentUser);
        friendship.setFriend(friend);

        connectionRepository.save(friendship);
    }

    @Override
    public List<Connection> getFriendshipList(int currentUserId) {
        List<Connection> friendshipList = connectionRepository.findByUserId(currentUserId);
        return friendshipList;
    }

    @Override
    public Connection getFriend() {
        return null;
    }
}
