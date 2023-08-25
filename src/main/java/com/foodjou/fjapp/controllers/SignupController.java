package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.dto.SignUpRequestDTO;
import com.foodjou.fjapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<String> addUser(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO) {
        userService.signUpUser(signUpRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }
}
