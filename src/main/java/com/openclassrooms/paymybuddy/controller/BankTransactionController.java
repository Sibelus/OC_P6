package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.BankTransaction;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IBankTransationService;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BankTransactionController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IBankTransationService iBankTransationService;
    Logger logger = LoggerFactory.getLogger(BankTransactionController.class);

    @GetMapping("/bankTransaction")
    public String bankTransactionPage(Model model){
        BankTransaction bankTransaction = new BankTransaction();
        User currentUser = iUserService.getCurrentUser();
        List<BankAccount> bankAccounts = currentUser.getBankAccountList();
        model.addAttribute("bankTransaction", bankTransaction);
        model.addAttribute("bankAccounts", bankAccounts);
        return "bankTransaction";
    }

    @PostMapping("/bankTransaction_bankToUser")
    public String bankToUserSubmit(@ModelAttribute BankTransaction bankTransaction, Model model){
        User currentUser = iUserService.getCurrentUser();
        List<BankAccount> bankAccounts = currentUser.getBankAccountList();
        bankTransaction.setUser(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bankTransaction", bankTransaction);
        try {
            iBankTransationService.bankToUser(bankTransaction);
            logger.debug("{} send {} from his bank account {}", currentUser.getFirstname(), bankTransaction.getAmount(), bankTransaction.getBankAccount());
            return "profile";
        } catch (InsufficientAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        } catch (NegativeAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        }
    }

    @PostMapping("/bankTransaction_userToBank")
    public String userToBankSubmit(@ModelAttribute BankTransaction bankTransaction, Model model){
        User currentUser = iUserService.getCurrentUser();
        List<BankAccount> bankAccounts = currentUser.getBankAccountList();
        bankTransaction.setUser(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("bankAccounts", bankAccounts);
        model.addAttribute("bankTransaction", bankTransaction);
        try {
            iBankTransationService.userToBank(bankTransaction);
            logger.debug("{} send {} to his bank account {}", currentUser.getFirstname(), bankTransaction.getAmount(), bankTransaction.getBankAccount());
            return "profile";
        } catch (InsufficientAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        } catch (NegativeAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "profile";
        }
    }
}
