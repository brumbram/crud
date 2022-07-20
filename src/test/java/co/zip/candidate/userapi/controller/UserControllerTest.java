package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.error.Constants;
import co.zip.candidate.userapi.error.InvalidInputError;
import co.zip.candidate.userapi.model.UserDetail;
import co.zip.candidate.userapi.model.UserDetailResponse;
import co.zip.candidate.userapi.model.UserInputRequest;
import co.zip.candidate.userapi.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private UserService userService;

    private UserDetailResponse testResponse;
    private UserDetail userDetail;
    private UserInputRequest testRequest;
    private UserInputRequest testFailRequest;
    private final String TEST_EMAIL = "dummy1@d.com";
    private final String MISSING_EMAIL = "12345@d.com";

    @BeforeEach
    public void init() {
        createUserDetail();
        createUserRequest();
        createUserResponse();
    }

    @Test
    public void getUserByEmailShouldSucceed() throws Exception {

        when(userService.getUser(TEST_EMAIL)).thenReturn(testResponse);

        mockMvc.perform(get("/user")
                       .param("email", "dummy1@d.com")
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userDetails", hasSize(1)))
               .andExpect(jsonPath("$.userDetails.[0].email").value(TEST_EMAIL));

    }

    @Test
    public void getUserByInvalidEmailShouldFail() throws Exception {

        when(userService.getUser(MISSING_EMAIL)).thenThrow(new InvalidInputError(Constants.Errors.USER_NOT_FOUND));

        mockMvc.perform(get("/user")
                       .param("email", MISSING_EMAIL)
                       .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorDetail", hasSize(1)))
               .andExpect(jsonPath("$.errorDetail.[0].code").value(Constants.Errors.USER_NOT_FOUND.getCode()));

    }

    @Test
    public void createUserShouldSucceed() throws Exception {

        when(userService.createUser(testRequest)).thenReturn(testResponse);

        mockMvc.perform(post("/user/create")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsString(testRequest)))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userDetails", hasSize(1)))
               .andExpect(jsonPath("$.userDetails.[0].email").value(TEST_EMAIL));

    }

    @Test
    public void createUserWithMissingFieldsShouldFail() throws Exception {

        mockMvc.perform(post("/user/create")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsString(testFailRequest)))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorDetail", hasSize(2)))
               .andExpect(jsonPath("$.errorDetail.[0].code").value(Constants.Errors.MISSING_FIELD_INPUT.getCode()))
               .andExpect(jsonPath("$.errorDetail.[1].code").value(Constants.Errors.MISSING_FIELD_INPUT.getCode()));

    }

    private void createUserDetail () {
        userDetail = UserDetail.builder().email(TEST_EMAIL).name("Dummy").monthlyExpense(BigDecimal.valueOf(1000)).monthlyExpense(BigDecimal.valueOf(100)).build();
    }

    public void createUserResponse() {
        testResponse = new UserDetailResponse(List.of(userDetail));
    }

    public void createUserRequest() {
        testRequest = new UserInputRequest();
        testRequest.setName("dummy_1");
        testRequest.setEmail(TEST_EMAIL);
        testRequest.setMonthlySalary(BigDecimal.valueOf(2000));
        testRequest.setMonthlyExpense(BigDecimal.valueOf(300));

        testFailRequest = new UserInputRequest();
        testFailRequest.setName("dummy_1");
        testFailRequest.setEmail("dummy2@d.com");
    }
}
