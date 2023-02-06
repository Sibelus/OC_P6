package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IConnectionService;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TransferController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IConnectionService iConnectionService;

    @GetMapping("/transfer")
    public String transferPage(Model model) {
        String email = "";
        List<User> friendList = new ArrayList<>();
        model.addAttribute("email", email);
        model.addAttribute("friendList", friendList);
        return "transfer";
    }

    @PostMapping("/transfer_newFriend")
    public String newFriendSubmit(@ModelAttribute String email, Model model) {
        model.addAttribute("email", email);
        //iConnectionService.addFriendship(email);
        return "transfer";
    }
}
