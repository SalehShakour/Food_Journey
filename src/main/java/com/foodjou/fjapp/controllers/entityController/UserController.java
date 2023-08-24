package com.foodjou.fjapp.controllers.entityController;

import com.foodjou.fjapp.services.entityService.UserService;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable String id, @Validated @RequestBody UserDTO updatedUserDTO) {
        userService.updateUserById(id, updatedUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");

    }
}