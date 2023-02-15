package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.ConnectionRepository;
import com.openclassrooms.paymybuddy.service.exceptions.AllReadyFrienWithException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.FriendWithMyselfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionService implements IConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private IUserService iUserService;
    Logger logger = LoggerFactory.getLogger(ConnectionService.class);


    @Override
    public void addFriendship(String email) throws AllReadyFrienWithException, FriendWithMyselfException, EmptyEmailException {
        Connection friendship = new Connection();
        User currentUser = iUserService.getCurrentUser();

        if(email == null){
            logger.error("email provided is null");
            throw new NullPointerException("email provided is incorrect: " + email);
        }
        if(email.equals("")){
            logger.error("email provided is empty");
            throw new EmptyEmailException("email provided is empty");
        }
        if(currentUser.getEmail().equals(email)){
            logger.error("You could'nt try to be your own friend => {} try to be friend with {}", currentUser.getEmail(), email);
            throw new FriendWithMyselfException("email provided is incorrect: you can't be friend with yourself");
        }

        User friend = iUserService.findByEmail(email).get();

        if (connectionRepository.existsByUserIdAndFriendId(currentUser.getId(), friend.getId())){
            logger.error("Friendship relation allready exists");
            throw new AllReadyFrienWithException("You allready are friend with " + friend.getFirstname());
        }

        friendship.setUser(currentUser);
        friendship.setFriend(friend);
        connectionRepository.save(friendship);
        logger.debug("Save new friendship");
    }

    @Override
    public List<Connection> getFriendshipList(int currentUserId) {
        List<Connection> friendshipList = connectionRepository.findByUserId(currentUserId);
        return friendshipList;
    }
}
