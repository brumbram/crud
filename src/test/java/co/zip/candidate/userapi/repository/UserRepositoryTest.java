package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final static String DUMMY_EMAIL = "dummy@test.com";

    @BeforeEach
    public void init() {
        User newUser = new User();
        newUser.setEmail(DUMMY_EMAIL);
        newUser.setName("dummy");
        newUser.setMonthlySalary(BigDecimal.valueOf(1000));
        newUser.setMonthlyExpense(BigDecimal.valueOf(100));

        userRepository.saveAndFlush(newUser);
    }

    @Test
    public void findByEmailShouldSucceed(){

        User user = userRepository.findByEmail(DUMMY_EMAIL);

        assertNotNull(user);
        assertEquals(user.getEmail(), DUMMY_EMAIL);

    }
}
