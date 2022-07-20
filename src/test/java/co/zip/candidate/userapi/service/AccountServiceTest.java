package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.error.Constants;
import co.zip.candidate.userapi.error.InvalidInputError;
import co.zip.candidate.userapi.model.AccountDetailResponse;
import co.zip.candidate.userapi.model.AccountInputRequest;
import co.zip.candidate.userapi.model.UserDetailResponse;
import co.zip.candidate.userapi.model.UserInputRequest;
import co.zip.candidate.userapi.model.entity.Account;
import co.zip.candidate.userapi.model.entity.User;
import co.zip.candidate.userapi.model.enums.AccountType;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    private User testUser;
    private Account testAccount;
    private AccountInputRequest testRequest;
    private AccountInputRequest testFailRequest;

    @BeforeEach
    public void init() {
        createTestUsers();
        createTestAccounts();
        createTestAccountInput();
    }

    @Test
    public void getAccountsByEmailShouldSucceed() {

        String searchEmail = "dummy1@d.com";
        when(userRepository.findByEmail(searchEmail)).thenReturn(testUser);

        when(accountRepository.findAllByUserId(testUser.getId())).thenReturn(getAccountList());

        AccountDetailResponse foundAccounts = accountService.getAccountsForUser(searchEmail);

        assertEquals(foundAccounts.getAccounts().size(), 1);
        assertEquals(foundAccounts.getEmail(), searchEmail);
        assertEquals(foundAccounts.getAccounts().get(0).getAccountType(), AccountType.ZIPPAY);
    }

    @Test
    public void getAccountsByEmailNotPresentShouldFail() {

        String searchEmail = "1233@d.com";
        when(userRepository.findByEmail(searchEmail)).thenReturn(null);

        InvalidInputError error = assertThrows(InvalidInputError.class, () -> {
            accountService.getAccountsForUser(searchEmail);
        });

        assertEquals(error.getCode(), Constants.Errors.USER_NOT_FOUND.getCode());
    }

    @Test
    public void createAccountShouldSucceed() {
        when(userRepository.findByEmail(testRequest.getEmail())).thenReturn(testUser);

        AccountDetailResponse createdAccount = accountService.createAccount(testRequest);

        assertNotNull(createdAccount);
        assertEquals(createdAccount.getEmail(), testRequest.getEmail());
        assertEquals(createdAccount.getAccounts().get(0).getAccountType(), testRequest.getAccountType());
    }

    @Test
    public void createAccountIfAlreadyExistsShouldFail() {
        when(userRepository.findByEmail(testRequest.getEmail())).thenReturn(testAccount.getUser());
        when(accountRepository.findByAccountType(AccountType.valueOf(testRequest.getAccountType()))).thenReturn(testAccount);

        InvalidInputError error = assertThrows(InvalidInputError.class, () -> {
            accountService.createAccount(testFailRequest);
        });

        assertEquals(Constants.Errors.ACCOUNT_ALREADY_EXISTS.getCode(), error.getCode());
    }


    public List<Account> getAccountList() {
        return List.of(testAccount);
    }

    public void createTestUsers() {
        testUser = new User();
        testUser.setName("dummy_1");
        testUser.setEmail("dummy1@d.com");
        testUser.setMonthlySalary(BigDecimal.valueOf(2000));
        testUser.setMonthlyExpense(BigDecimal.valueOf(100));
    }

    public void createTestAccounts() {
        testAccount = new Account();
        testAccount.setAccountType(AccountType.ZIPPAY);
        testAccount.setUser(testUser);
    }

    public void createTestAccountInput () {
        testRequest = new AccountInputRequest();
        testRequest.setEmail("dummy1@d.com");
        testRequest.setAccountType("ZIPPAY");

        testFailRequest = new AccountInputRequest();
        testFailRequest.setEmail("dummy1@d.com");
        testFailRequest.setAccountType("ZIPPPAY");

    }
}
