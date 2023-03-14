package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class OauthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService iUserService;
    Logger logger = LoggerFactory.getLogger(OauthController.class);


    @GetMapping("/oauthLogin")
    public String oauthConnexion(Principal user, Model model){
        String githubId = user.getName();
        model.addAttribute("user", new User());

        if ((userRepository.existsByGithub(githubId))){
            User userGithub = userRepository.findByGithub(githubId);
            logger.debug("{} {} connect application with his github account", userGithub.getFirstname(), userGithub.getLastname());
            return "redirect:/home";
        } else {
            model.addAttribute("user", new User());
            return "homeOauth2";
        }
    }


    @GetMapping("/homeOauth2")
    public String createOauthAccountPage(Model model) {
        model.addAttribute("user", new User());
        return "homeOauth2";
    }

    @PostMapping("/homeOauth2")
    public String createOauthAccountSubmit(@ModelAttribute User user, Model model, Principal userToken){
        model.addAttribute("user", user);
        try {
            user.setGithub(userToken.getName());
            iUserService.addOauthUser(user);
            logger.debug("{} {} create his account with id: {}", user.getFirstname(), user.getLastname(), user.getEmail());
            return "home";
        } catch (EmptyFirstnameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "homeOauth2";
        } catch (EmptyLastnameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "homeOauth2";
        } catch (EmptyEmailException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "homeOauth2";
        }
    }
}
