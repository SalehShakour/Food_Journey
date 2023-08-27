package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @PostMapping("/")
    public ResponseEntity<String> createOrder(@AuthenticationPrincipal User currentUser){
        //todo
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("This feature not ready to use");

    }

}
