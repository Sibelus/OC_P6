package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyFirstnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyLastnameException;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditAccountController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/editAccount")
    public String editAccountPage(Model model){
        User currentUser = iUserService.getCurrentUser();
        User user = new User();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        return "editAccount";
    }

    @PostMapping("/editAccount")
    public String editAccountSubmit(@ModelAttribute User user, Model model){
        User currentUser = iUserService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        try {
            iUserService.updateUser(user);
            return "profile";
        } catch (EmptyFirstnameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        } catch (EmptyLastnameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        } catch (EmptyEmailException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        } catch (EmptyPasswordException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        }
    }

    @GetMapping("/editAccount_delete")
    public String editAccountDelete(Model model){
        User currentUser = iUserService.getCurrentUser();
        iUserService.deleteUser(currentUser);
        return "/login";
    }
}
