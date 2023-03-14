package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IBankAccountService;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyBankAccountNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddBankAccountController {

    @Autowired
    private IBankAccountService iBankAccountService;
    @Autowired
    private IUserService iUserService;
    Logger logger = LoggerFactory.getLogger(AddBankAccountController.class);

    @GetMapping("/addBankAccount")
    public String createAccountForm(Model model) {
        model.addAttribute("bankAccount", new BankAccount());
        return "addBankAccount";
    }

    @PostMapping("/addBankAccount")
    public String createAccountSubmit(@ModelAttribute BankAccount bankAccount, Model model) {
        User currentUser = iUserService.getCurrentUser();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bankAccount", bankAccount);
        try {
            iBankAccountService.addBankAccount(bankAccount);
            logger.debug("{} add new bank account {}", currentUser.getFirstname(), bankAccount.getName());
            return "redirect:/profile";
        } catch (EmptyBankAccountNameException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        }
    }
}
