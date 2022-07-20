package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.error.Constants;
import co.zip.candidate.userapi.error.InvalidInputError;
import co.zip.candidate.userapi.model.AccountDetail;
import co.zip.candidate.userapi.model.AccountDetailResponse;
import co.zip.candidate.userapi.model.AccountInputRequest;
import co.zip.candidate.userapi.model.entity.Account;
import co.zip.candidate.userapi.model.entity.User;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static co.zip.candidate.userapi.util.ModelMapper.mapToAccountDetail;

@Slf4j
@Service
public class AccountService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountDetailResponse getAccountsForUser(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        log.debug(" user is present {} = {}", email, user.isPresent());
        if (!user.isPresent()) {
            throw new InvalidInputError(Constants.Errors.USER_NOT_FOUND);
        }

        List<Account> accounts = accountRepository.findAllByUserId(user.get().getId());

        List<AccountDetail> accountDetails = accounts.stream().map(x -> mapToAccountDetail(x)).collect(Collectors.toList());

        return new AccountDetailResponse(user.get().getName(), user.get().getEmail(), accountDetails);
    }

    public AccountDetailResponse createAccount(AccountInputRequest request) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()));

        if (!user.isPresent()) {
            throw new InvalidInputError(Constants.Errors.USER_NOT_FOUND);
        }

        Optional<Account> account = Optional.ofNullable(accountRepository.findByAccountType(request.getAccountType()));

        if (account.isPresent()) {
            throw new InvalidInputError(Constants.Errors.ACCOUNT_ALREADY_EXISTS);
        }

        Account newAccount = new Account();
        newAccount.setUser(user.get());
        newAccount.setAccountType(request.getAccountType());

        accountRepository.saveAndFlush(newAccount);

        return new AccountDetailResponse(user.get().getName(), user.get().getEmail(), List.of(mapToAccountDetail(newAccount)));
    }

}
