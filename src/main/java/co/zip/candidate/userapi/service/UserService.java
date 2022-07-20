package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.error.Constants;
import co.zip.candidate.userapi.error.InvalidInputError;
import co.zip.candidate.userapi.model.UserDetail;
import co.zip.candidate.userapi.model.UserDetailResponse;
import co.zip.candidate.userapi.model.UserInputRequest;
import co.zip.candidate.userapi.model.entity.User;
import co.zip.candidate.userapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.zip.candidate.userapi.util.ModelMapper.mapToUserDetail;

@Slf4j
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDetail> userDetails=  users.stream().map(x -> mapToUserDetail(x))
                                     .collect(Collectors.toList());

        return new UserDetailResponse(userDetails);
    }

    public UserDetailResponse getUser(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (!user.isPresent()) {
            throw new InvalidInputError(Constants.Errors.USER_NOT_FOUND);
        }

        return new UserDetailResponse(List.of(mapToUserDetail(user.get())));
    }

    public UserDetailResponse createUser(UserInputRequest request) {
        checkEligibility(request.getMonthlyExpense(), request.getMonthlySalary());

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));

        if (user.isPresent())
        {
            throw new InvalidInputError(Constants.Errors.EMAIL_ADDRESS_ALREADY_EXISTS);
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setName(request.getName());
        newUser.setMonthlySalary(request.getMonthlySalary());
        newUser.setMonthlyExpense(request.getMonthlyExpense());

        userRepository.saveAndFlush(newUser);

        return new UserDetailResponse(List.of(mapToUserDetail(newUser)));
    }

    private void checkEligibility(BigDecimal monthlyExpense, BigDecimal monthlySalary) {

        if (monthlySalary.subtract(monthlyExpense).compareTo(BigDecimal.valueOf(1000)) <= 0) {
            throw new InvalidInputError(Constants.Errors.EXPENSE_THRESHOLD_ERROR);
        }
    }
}
