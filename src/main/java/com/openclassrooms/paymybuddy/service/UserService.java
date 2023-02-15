package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    Logger logger  = LoggerFactory.getLogger(UserService.class);


    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User addUser(User user) throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
        if(user.getFirstname().equals("")){
            logger.error("Firstname provided is empty");
            throw new EmptyFirstnameException("Firstname provided is empty, you must set a valid one");
        }
        if(user.getLastname().equals("")){
            logger.error("Firstname provided is empty");
            throw new EmptyLastnameException("Lastname provided is empty, you must set a valid one");
        }
        if(user.getEmail().equals("")){
            logger.error("Email provided is empty");
            throw new EmptyEmailException("Email provided is empty, you must set a valid one");
        }
        if(user.getPassword().equals("")){
            logger.error("Password provided is empty");
            throw new EmptyPasswordException("Password provided is empty, you must set a valid one");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        logger.debug("Save new user");
        return userRepository.save(user);
    }


    //----------- Get current user info -----------
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserEmail = authentication.getName();
        }
        User currentUser = findByEmail(currentUserEmail).get();
        return currentUser;
    }
}
