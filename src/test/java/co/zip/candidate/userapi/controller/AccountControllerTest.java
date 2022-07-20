package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.error.Constants;
import co.zip.candidate.userapi.error.InvalidInputError;
import co.zip.candidate.userapi.model.*;
import co.zip.candidate.userapi.model.enums.AccountType;
import co.zip.candidate.userapi.service.AccountService;
import co.zip.candidate.userapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private AccountService accountService;

    private AccountDetail accountDetail;
    private AccountInputRequest testRequest;
    private AccountDetailResponse testResponse;
    private final String TEST_EMAIL = "dummy1@d.com";
    private final String MISSING_EMAIL = "12345@d.com";

    @BeforeEach
    public void init() {
        createAccountDetail();
        createAccountRequest();
        createAccountResponse();
    }

    @Test
    public void getAccountByUserEmailShouldSucceed() throws Exception {

        when(accountService.getAccountsForUser(TEST_EMAIL)).thenReturn(testResponse);

        mockMvc.perform(get("/account")
                       .param("email", "dummy1@d.com")
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.email").value(TEST_EMAIL))
               .andExpect(jsonPath("$.accounts.[0].accountType").value(AccountType.ZIPPAY.toString()));

    }

    @Test
    public void getUserByInvalidEmailShouldFail() throws Exception {

        when(accountService.getAccountsForUser(MISSING_EMAIL)).thenThrow(new InvalidInputError(Constants.Errors.USER_NOT_FOUND));

        mockMvc.perform(get("/account")
                       .param("email", MISSING_EMAIL)
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorDetail", hasSize(1)))
               .andExpect(jsonPath("$.errorDetail.[0].code").value(Constants.Errors.USER_NOT_FOUND.getCode()));

    }

    @Test
    public void createAccountShouldSucceed() throws Exception {

        when(accountService.createAccount(testRequest)).thenReturn(testResponse);

        mockMvc.perform(post("/account/create")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsString(testRequest)))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.accounts", hasSize(1)))
               .andExpect(jsonPath("$.accounts.[0].accountType").value(AccountType.ZIPPAY.toString()));

    }

    @Test
    public void createAccountWithMissingFieldsShouldFail() throws Exception {
        testRequest.setAccountType(null);
        mockMvc.perform(post("/account/create")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsString(testRequest)))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorDetail", hasSize(1)))
               .andExpect(jsonPath("$.errorDetail.[0].code").value(Constants.Errors.INVALID_FIELD_INPUT.getCode()));

    }

    private void createAccountDetail () {
        accountDetail = AccountDetail.builder().accountType(AccountType.ZIPPAY).accountId(Long.valueOf("1")).build();
    }

    public void createAccountResponse() {
        testResponse = new AccountDetailResponse("dummy", TEST_EMAIL,  List.of(accountDetail));
    }

    public void createAccountRequest() {

        testRequest = new AccountInputRequest();
        testRequest.setEmail(TEST_EMAIL);
        testRequest.setAccountType("ZIPPAY");
    }
}
