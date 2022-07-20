package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.AccountDetailResponse;
import co.zip.candidate.userapi.model.AccountInputRequest;
import co.zip.candidate.userapi.service.AccountService;
import co.zip.candidate.userapi.validator.ValidEmail;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/account")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDetailResponse> getAccounts(@RequestParam @ValidEmail String email) {

        return ResponseEntity.ok(accountService.getAccountsForUser(email));
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDetailResponse> createUser(@RequestBody @Valid AccountInputRequest request) {

        return ResponseEntity.ok(accountService.createAccount(request));
    }

}
