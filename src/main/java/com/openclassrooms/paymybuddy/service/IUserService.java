package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {
    Iterable<User> getUsers();
    Optional<User> getUserById(Integer id);
    Optional<User> findByEmail(String email);
    User addUser(User user);


    //----------- Get current user info -----------
    User getCurrentUser();
}
