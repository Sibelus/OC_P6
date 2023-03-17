package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.*;
import com.openclassrooms.paymybuddy.service.IBankTransationService;
import com.openclassrooms.paymybuddy.service.IInappTransactionService;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.InsufficientAmountException;
import com.openclassrooms.paymybuddy.service.exceptions.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransferController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IInappTransactionService iInappTransactionService;
    @Autowired
    private IBankTransationService iBankTransationService;
    Logger logger = LoggerFactory.getLogger(TransferController.class);


    @GetMapping("/transfer")
    public String transferPage(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                               @RequestParam("pageBank") Optional<Integer> pageBank, @RequestParam("sizeBank") Optional<Integer> sizeBank) {
        InAppTransaction inAppTransaction = new InAppTransaction();
        User currentUser = iUserService.getCurrentUser();
        List<Connection> friends = currentUser.getFriendsList();
        List<InAppTransaction> inAppTransactions = currentUser.getInAppTransactions();
        List<BankTransaction> bankTransactions = currentUser.getBankTransactions();

        //Pagination for inAppTransactions table
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<InAppTransaction> inAppTransactionPage = iInappTransactionService.findPaginatedInApp(PageRequest.of(currentPage - 1, pageSize), inAppTransactions);

        model.addAttribute("inAppTransactionPage", inAppTransactionPage);

        int totalPages = inAppTransactionPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        //Pagination for bankTransactions table
        int currentPageBank = pageBank.orElse(1);
        int pageSizeBank = sizeBank.orElse(5);
        Page<BankTransaction> bankTransactionPage = iBankTransationService.findPaginatedBank(PageRequest.of(currentPageBank - 1, pageSizeBank), bankTransactions);

        model.addAttribute("bankTransactionPage", bankTransactionPage);

        int totalPagesBank = bankTransactionPage.getTotalPages();
        if (totalPagesBank > 0) {
            List<Integer> pageNumbersBank = IntStream.rangeClosed(1, totalPagesBank)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbersBank", pageNumbersBank);
        }

        model.addAttribute("friends", friends);
        model.addAttribute("inAppTransaction", inAppTransaction);
        model.addAttribute("inAppTransactions", inAppTransactions);
        model.addAttribute("bankTransactions", bankTransactions);
        return "transfer";
    }


    @PostMapping("/transfer")
    public String sendMoneyToFriendSubmit(@ModelAttribute InAppTransaction inAppTransaction, Model model){
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
            return "error";
        } catch (NegativeAmountException e) {
            String errorMessage = (e.getMessage());
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }
    }
}
