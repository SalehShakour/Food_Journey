package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.services.UserService;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final MapStructUser mapStructUser;

    @GetMapping
    public ResponseEntity<UserDTO> showProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.OK).body(mapStructUser.userToUserDTO(currentUser));

    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ROLE_ADMIN","ROLE_SUPER_ADMIN"})
    public ResponseEntity<String> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateUserById(@AuthenticationPrincipal User currentUser,
                                                 @Validated @RequestBody UserDTO updatedUserDTO) {
        userService.updateUserById(currentUser, updatedUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully. if your email changed, login again.");

    }
}
