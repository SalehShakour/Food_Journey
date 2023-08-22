package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.services.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("")
    public ResponseEntity<String> addUser(@RequestBody User user){
        try{
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User data is not valid");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }catch (DataIntegrityViolationException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id){
        try{
            userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }catch (EmptyResultDataAccessException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable String id, @RequestBody User updatedUser){
        try{
            userService.updateUserById(id, updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
        }catch (EmptyResultDataAccessException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }
}
