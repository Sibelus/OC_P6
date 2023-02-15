package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CreateAccountController {

    @Autowired
    private IUserService iUserService;
    Logger logger = LoggerFactory.getLogger(CreateAccountController.class);

    @GetMapping("/createAccount")
    public String createAccountForm(Model model) {
        model.addAttribute("user", new User());
        return "createAccount";
    }

    @PostMapping("/createAccount")
    public String createAccountSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        try {
            iUserService.addUser(user);
            logger.debug("{} {} create his account with id: {} & password: {}", user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
            return "login";
        } catch (EmptyFirstnameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        } catch (EmptyLastnameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        } catch (EmptyEmailException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        } catch (EmptyPasswordException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }
    }
}
