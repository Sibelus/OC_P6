package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.service.IInappTransactionService;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransferController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IInappTransactionService iInappTransactionService;
    Logger logger = LoggerFactory.getLogger(TransferController.class);


    @GetMapping("/transfer")
    public String transferPage(Model model) {
        InAppTransaction inAppTransaction = new InAppTransaction();
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friends = currentUser.getFriendsList();
        List<InAppTransaction> inAppTransactions = currentUser.getInAppTransactions();
        List<BankTransaction> bankTransactions = currentUser.getBankTransactions();

        model.addAttribute("friends", friends);
        model.addAttribute("inAppTransaction", inAppTransaction);
        model.addAttribute("inAppTransactions", inAppTransactions);
        model.addAttribute("bankTransactions", bankTransactions);
        return "transfer";
    }


    @PostMapping("/transfer")
    public String bankToUserSubmit(@ModelAttribute InAppTransaction inAppTransaction, Model model){
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friends = currentUser.getFriendsList();
        List<InAppTransaction> inAppTransactions = currentUser.getInAppTransactions();
        List<BankTransaction> bankTransactions = currentUser.getBankTransactions();

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("friends", friends);
        model.addAttribute("inAppTransaction", inAppTransaction);
        model.addAttribute("inAppTransactions", inAppTransactions);
        model.addAttribute("bankTransactions", bankTransactions);

        inAppTransaction.setSender(currentUser);
        try {
            iInappTransactionService.sendMoneyToFriend(inAppTransaction);
            logger.debug("{} send {} to {}", currentUser.getFirstname(), inAppTransaction.getAmount(), inAppTransaction.getReceiver().getFirstname());
            return "redirect:/transfer";
        } catch (InsufficientAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "transfer";
        } catch (NegativeAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "transfer";
        }
    }
}
