package com.openclassrooms.paymybuddy.controller;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.repository.UserRepository;
import com.openclassrooms.paymybuddy.service.IBankAccountService;
import com.openclassrooms.paymybuddy.service.IBankTransationService;
import com.openclassrooms.paymybuddy.service.IUserService;
import com.openclassrooms.paymybuddy.service.exceptions.EmptyBankAccountNameException;
import com.openclassrooms.paymybuddy.service.exceptions.FriendWithMyselfException;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    /* ------- AddBankAccountController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetAddBankAccount() throws Exception {
        mockMvc.perform(get("/addBankAccount")).andExpect(status().isOk());
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostAddBankAccount() throws Exception {
        mockMvc.perform(post("/addBankAccount")
                .param("name", "La bonne banque")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/profile"));
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostAddBankAccount_EmptyBankAccountNameExceptionhException() throws Exception {
        mockMvc.perform(post("/addBankAccount")
                        .param("name", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Bank account name provided is empty"));
    }


    /* ------- BankTransactionController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetBankTransaction() throws Exception {
        mockMvc.perform(get("/bankTransaction")).andExpect(status().isOk());
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostBankTransaction_BankToUser() throws Exception {
        mockMvc.perform(post("/bankTransaction_bankToUser")
                        .param("bankAccount", "1")
                        .param("amount", "500")
                        .param("comment", "Let's have some fun !")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(value = "bafritte@mail.fr")
    @Test
    public void testPostBankTransaction_BankToUser_InsufficientAmountException() throws Exception {
        mockMvc.perform(post("/bankTransaction_bankToUser")
                        .param("bankAccount", "2")
                        .param("amount", "5000")
                        .param("comment", "Let's have some fun !")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "insufficient amount: you only have 1200 on your bank account"));
    }

    @WithMockUser(value = "bafritte@mail.fr")
    @Test
    public void testPostBankTransaction_BankToUser_NegativeAmountException() throws Exception {
        mockMvc.perform(post("/bankTransaction_bankToUser")
                        .param("bankAccount", "2")
                        .param("amount", "-10")
                        .param("comment", "Let's have some fun !")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "You can't send negative amount: -10"));
    }

    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostBankTransaction_UserToBank() throws Exception {
        mockMvc.perform(post("/bankTransaction_userToBank")
                        .param("bankAccount", "5")
                        .param("amount", "500")
                        .param("comment", "Let's have some fun !")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostBankTransaction_UserToBank_InsufficientAmountException() throws Exception {
        mockMvc.perform(post("/bankTransaction_userToBank")
                        .param("bankAccount", "1")
                        .param("amount", "5000")
                        .param("comment", "Let's have some fun !")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "insufficient amount: you only have 0 on your account"));
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostBankTransaction_UserToBank_NegativeAmountException() throws Exception {
        mockMvc.perform(post("/bankTransaction_userToBank")
                        .param("bankAccount", "1")
                        .param("amount", "-10")
                        .param("comment", "Let's have some fun !")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "You can't send negative amount: -10"));
    }


    /* ------- ContactController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetContact() throws Exception {
        mockMvc.perform(get("/contact")).andExpect(status().isOk());
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostContact_NewFriendSubmit() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("email", "sbihaille@mail.fr")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostContact_NewFriendSubmit_AllReadyFriendWithException() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("email", "bafritte@mail.fr")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "You allready are friend with Barak"));
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostContact_NewFriendSubmit_FriendWithMyselfException() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("email", "mtampion@mail.fr")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "email provided is incorrect: you can't be friend with yourself"));
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostContact_NewFriendSubmit_EmptyEmailException() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("email", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "email provided is empty"));
    }


    /* ------- CreateAccountController ------- */
    @Test
    public void testGetCreateAccount() throws Exception {
        mockMvc.perform(get("/createAccount")).andExpect(status().isOk());
    }

    @Test
    public void testPostCreateAccount() throws Exception {
        mockMvc.perform(post("/createAccount")
                        .param("firstname", "Florence")
                        .param("lastname", "Aignement")
                        .param("email", "faignement@mail.fr")
                        .param("password", "123")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testPostCreateAccount_EmptyFirstnameException() throws Exception {
        mockMvc.perform(post("/createAccount")
                        .param("firstname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Firstname provided is empty, you must set a valid one"));
    }

    @Test
    public void testPostCreateAccount_EmptyLastnameException() throws Exception {
        mockMvc.perform(post("/createAccount")
                        .param("firstname", "Florence")
                        .param("lastname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Lastname provided is empty, you must set a valid one"));
    }

    @Test
    public void testPostCreateAccount_EmptyEmailException() throws Exception {
        mockMvc.perform(post("/createAccount")
                        .param("firstname", "Florence")
                        .param("lastname", "Aignement")
                        .param("email", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Email provided is empty, you must set a valid one"));
    }

    @Test
    public void testPostCreateAccount_EmptyPasswordException() throws Exception {
        mockMvc.perform(post("/createAccount")
                        .param("firstname", "Florence")
                        .param("lastname", "Aignement")
                        .param("email", "faignement@mail.fr")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Password provided is empty, you must set a valid one"));
    }


    /* ------- EditAccountController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetEditAccount() throws Exception {
        mockMvc.perform(get("/editAccount")).andExpect(status().isOk());
    }

    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostEditAccount() throws Exception {
        mockMvc.perform(post("/editAccount")
                        .param("firstname", "Steffe")
                        .param("lastname", "Bihaille")
                        .param("email", "sbihaille@mail.fr")
                        .param("password", "123")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostEditAccount_EmptyFirstnameException() throws Exception {
        mockMvc.perform(post("/editAccount")
                        .param("firstname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Firstname provided is empty, you must set a valid one"));
    }

    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostEditAccount_EmptyLastnameException() throws Exception {
        mockMvc.perform(post("/editAccount")
                        .param("firstname", "Steff")
                        .param("lastname", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Lastname provided is empty, you must set a valid one"));
    }

    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostEditAccount_EmptyEmailException() throws Exception {
        mockMvc.perform(post("/editAccount")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("email", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Email provided is empty, you must set a valid one"));
    }

    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostEditAccount_EmptyPasswordException() throws Exception {
        mockMvc.perform(post("/editAccount")
                        .param("firstname", "Steff")
                        .param("lastname", "Bihaille")
                        .param("email", "sbihaille@mail.fr")
                        .param("password", "")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Password provided is empty, you must set a valid one"));
    }


    /* ------- HomeController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/home")).andExpect(status().isOk());
    }


    /* ------- ProfileController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetProfile() throws Exception {
        mockMvc.perform(get("/profile")).andExpect(status().isOk());
    }


    /* ------- TransferController ------- */
    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testGetTransfer() throws Exception {
        mockMvc.perform(get("/transfer")).andExpect(status().isOk());
    }


    @WithMockUser(value = "sbihaille@mail.fr")
    @Test
    public void testPostTransfer() throws Exception {
        mockMvc.perform(post("/transfer")
                        .param("receiver", "2")
                        .param("amount", "20")
                        .param("comment", "Sunday brunch")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostTransfer_InsufficientAmountException() throws Exception {
        mockMvc.perform(post("/transfer")
                        .param("receiver", "2")
                        .param("amount", "200")
                        .param("comment", "Sunday brunch")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "insufficient amount: you only have 0"));
    }

    @WithMockUser(value = "mtampion@mail.fr")
    @Test
    public void testPostTransfer_NegativeAmountException() throws Exception {
        mockMvc.perform(post("/transfer")
                        .param("receiver", "2")
                        .param("amount", "-100")
                        .param("comment", "Sunday brunch")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "You can't send negative amount: -100"));
    }
}
