package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.error.Constants;
import co.zip.candidate.userapi.error.InvalidInputError;
import co.zip.candidate.userapi.model.UserDetailResponse;
import co.zip.candidate.userapi.model.UserInputRequest;
import co.zip.candidate.userapi.model.entity.User;
import co.zip.candidate.userapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User testUser;
    private User testFailUser;
    private UserInputRequest testRequest;
    private UserInputRequest testFailRequest;

    @BeforeEach
    public void init() {
        createTestUsers();
        createTestUserInput();
    }

    @Test
    public void getAllUsersShouldSucceed() {

        when(userRepository.findAll()).thenReturn(getUserList());

        UserDetailResponse userDetails = userService.getAllUsers();

        assertEquals(userDetails.getUserDetails().size(), getUserList().size());

    }

    @Test
    public void getUserByEmailShouldSucceed() {

        String searchEmail = "dummy1@d.com";
        when(userRepository.findByEmail(searchEmail)).thenReturn(testUser);

        UserDetailResponse foundUser = userService.getUser(searchEmail);

        assertEquals(foundUser.getUserDetails().size(), 1);
        assertEquals(foundUser.getUserDetails().get(0).getEmail(), searchEmail);
    }

    @Test
    public void createUserShouldSucceed() {
        when(userRepository.findByEmail(testRequest.getEmail())).thenReturn(null);

        UserDetailResponse createdUser = userService.createUser(testRequest);

        assertNotNull(createdUser);
        assertEquals(createdUser.getUserDetails().get(0).getEmail(), testRequest.getEmail());
        assertEquals(createdUser.getUserDetails().get(0).getName(), testRequest.getName());
        assertEquals(createdUser.getUserDetails().get(0).getMonthlyExpense(), testRequest.getMonthlyExpense());
        assertEquals(createdUser.getUserDetails().get(0).getMonthlySalary(), testRequest.getMonthlySalary());
    }

    @Test
    public void createUserWithHigherExpenseShouldFail() {

        InvalidInputError error = assertThrows(InvalidInputError.class, () -> {
            userService.createUser(testFailRequest);
        });

        assertEquals(error.getCode(), Constants.Errors.EXPENSE_THRESHOLD_ERROR.getCode());
    }

    @Test
    public void createUserWithDuplicateEmailShouldFail() {
        when(userRepository.findByEmail(testRequest.getEmail())).thenReturn(testUser);

        InvalidInputError error = assertThrows(InvalidInputError.class, () -> {
            userService.createUser(testRequest);
        });

        assertEquals(error.getCode(), Constants.Errors.EMAIL_ADDRESS_ALREADY_EXISTS.getCode());
    }

    public List<User> getUserList() {
        return List.of(testUser, testFailUser);
    }

    public void createTestUsers() {
        testUser = new User();
        testUser.setName("dummy_1");
        testUser.setEmail("dummy1@d.com");
        testUser.setMonthlySalary(BigDecimal.valueOf(2000));
        testUser.setMonthlyExpense(BigDecimal.valueOf(100));

        testFailUser = new User();
        testFailUser.setName("dummy_2");
        testFailUser.setEmail("dummy2@d.com");
        testFailUser.setMonthlySalary(BigDecimal.valueOf(1000));
        testFailUser.setMonthlyExpense(BigDecimal.valueOf(300));
    }

    public void createTestUserInput () {
        testRequest = new UserInputRequest();
        testRequest.setName("dummy_1");
        testRequest.setEmail("dummy2@d.com");
        testRequest.setMonthlySalary(BigDecimal.valueOf(2000));
        testRequest.setMonthlyExpense(BigDecimal.valueOf(300));

        testFailRequest = new UserInputRequest();
        testFailRequest.setName("dummy_1");
        testFailRequest.setEmail("dummy2@d.com");
        testFailRequest.setMonthlySalary(BigDecimal.valueOf(1000));
        testFailRequest.setMonthlyExpense(BigDecimal.valueOf(300));
    }
}
