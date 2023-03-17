package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyPasswordException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    private EntityManager entityManager;
    Logger logger  = LoggerFactory.getLogger(UserService.class);


    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public void addUser(User user) throws EmptyEmailException, EmptyFirstnameException, EmptyPasswordException, EmptyLastnameException {
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
        userRepository.save(user);
    }

    @Override
    public void addOauthUser(User user) throws EmptyEmailException, EmptyFirstnameException, EmptyLastnameException {
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

        logger.debug("Save new Oauth user");
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) throws EmptyFirstnameException, EmptyLastnameException, EmptyEmailException, EmptyPasswordException {
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

        User currentUser = getCurrentUser();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());

        currentUser.setFirstname(user.getFirstname());
        currentUser.setLastname(user.getLastname());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(password);
        userRepository.save(currentUser);
        logger.debug("Update user infos");
    }

    @Override
    public void deleteUser(User user) throws InternalAuthenticationServiceException {
        userRepository.delete(user);
    }

    //----------- Get current user info -----------
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserEmail = authentication.getName();
        }
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User principal = (OAuth2User) authentication.getPrincipal();
            String githubId = principal.getName();
            currentUserEmail = userRepository.findByGithub(githubId).getEmail();
        }

        User currentUser = findByEmail(currentUserEmail).get();
        return currentUser;
    }
}
