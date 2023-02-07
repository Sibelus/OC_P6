package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
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
    UserRepository userRepository;

    @Override
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public User addUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
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
