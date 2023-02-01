package com.openclassrooms.paymybuddy.service;

import com.openclassrooms.paymybuddy.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("USER")));
    }
}
