package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IConnectionService;
import com.openclassrooms.paymybuddy.service.IUserService;
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
    IUserService iUserService;
    @Autowired
    IConnectionService iConnectionService;

    @GetMapping("/contact")
    public String contactPage(Model model) {
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friendsList = iConnectionService.getFriendshipList(currentUser.getId());
        model.addAttribute("friendList", friendsList);
        return "contact";
    }

    @PostMapping("/contact_newFriend")
    public String newFriendSubmit(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        Optional<User> friend = iUserService.findByEmail(email);
        iConnectionService.addFriendship(email);
        return "contact";
    }
}
