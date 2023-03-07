package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.exceptions.AllReadyFrienWithException;
import com.openclassrooms.paymybuddy.service.IConnectionService;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyEmailException;
import com.openclassrooms.paymybuddy.service.exceptions.FriendWithMyselfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ContactController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IConnectionService iConnectionService;
    Logger logger = LoggerFactory.getLogger(ContactController.class);


    @GetMapping("/contact")
    public String contactPage(Model model) {
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friendsList = iConnectionService.getFriendshipList(currentUser.getId());
        model.addAttribute("friendList", friendsList);
        return "contact";
    }

    @PostMapping("/contact")
    public String newFriendSubmit(@RequestParam String email, Model model) {
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friendsList = iConnectionService.getFriendshipList(currentUser.getId());
        Optional<User> friend = iUserService.findByEmail(email);

        model.addAttribute("friendList", friendsList);
        model.addAttribute("email", email);

        try {
            iConnectionService.addFriendship(email);
            logger.debug("{} become friend with {}", currentUser.getFirstname(), friend.get().getFirstname());
            return "redirect:/contact";
        } catch (AllReadyFrienWithException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "contact";
        } catch (FriendWithMyselfException e ) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "contact";
        } catch (EmptyEmailException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "contact";
        } catch (NullPointerException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "contact";
        }

    }
}
