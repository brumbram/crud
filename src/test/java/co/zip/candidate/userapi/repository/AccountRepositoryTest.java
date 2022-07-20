package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.entity.Account;
import co.zip.candidate.userapi.model.entity.User;
import co.zip.candidate.userapi.model.enums.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    private final static String DUMMY_EMAIL = "dummy2@test.com";

    @BeforeEach
    public void init() {
        User newUser = new User();
        newUser.setEmail(DUMMY_EMAIL);
        newUser.setName("dummy");
        newUser.setMonthlySalary(BigDecimal.valueOf(1000));
        newUser.setMonthlyExpense(BigDecimal.valueOf(100));

        Account newAccount = new Account();
        newAccount.setUser(newUser);
        newAccount.setAccountType(AccountType.ZIPPAY);

        accountRepository.saveAndFlush(newAccount);
    }

    @Test
    public void findByAccountTypeShouldSucceed(){

        Account account = accountRepository.findByAccountType(AccountType.ZIPPAY);

        assertNotNull(account);
        assertEquals(account.getAccountType(), AccountType.ZIPPAY);
    }

    @Test
    public void findByAccountByUserIdShouldSucceed(){

        List<Account> account = accountRepository.findAllByUserId(Long.valueOf("1"));

        assertEquals(account.size(), 1);
        assertEquals(account.get(0).getAccountType(), AccountType.ZIPPAY);

    }
}
