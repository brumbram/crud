package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.UserDetail;
import co.zip.candidate.userapi.model.UserInputRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetail> getUser(@RequestParam String email) {

        return ResponseEntity.ok(new UserDetail());
    }

    @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDetail>> getAllUsers() {

        return ResponseEntity.ok(List.of(new UserDetail()));
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetail> createUser(@RequestBody UserInputRequest request) {

        return ResponseEntity.ok(new UserDetail());
    }

}
