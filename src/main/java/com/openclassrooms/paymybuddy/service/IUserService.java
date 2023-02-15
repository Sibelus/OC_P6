package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyPasswordException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserService {

    Optional<User> findByEmail(String email);
    User addUser(User user) throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException;


    //----------- Get current user info -----------
    User getCurrentUser();
}
