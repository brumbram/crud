package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.UserDetailResponse;
import co.zip.candidate.userapi.model.UserInputRequest;
import co.zip.candidate.userapi.service.UserService;
import co.zip.candidate.userapi.validator.ValidEmail;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> getUser(@RequestParam @ValidEmail String email) {

        return ResponseEntity.ok(userService.getUser(email));
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> createUser(@RequestBody @Valid UserInputRequest request) {

        return ResponseEntity.ok(userService.createUser(request));
    }

}
