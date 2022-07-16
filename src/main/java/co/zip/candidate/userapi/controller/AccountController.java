package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.AccountDetail;
import co.zip.candidate.userapi.model.AccountInputRequest;
import co.zip.candidate.userapi.model.UserDetail;
import co.zip.candidate.userapi.model.UserInputRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/account")
public class AccountController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDetail> getAccounts(@RequestParam String email) {

        return ResponseEntity.ok(new AccountDetail());
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDetail> createUser(@RequestBody AccountInputRequest request) {

        return ResponseEntity.ok(new AccountDetail());
    }

}
