package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.services.AvailableRole;
import com.foodjou.fjapp.services.RoleService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserRepository userRepository;

    public AdminController(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @PutMapping("/addAdminRole/{userId}")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public ResponseEntity<String> addAdminRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User not found by email"));
        roleService.addRoleToUser(user, AvailableRole.ROLE_ADMIN);
        return ResponseEntity.status(HttpStatus.OK).body(user.getRoles().toString());
    }

    @PutMapping("/removeAdminRole/{userId}")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public ResponseEntity<String> removeAdminRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User not found by email"));
        roleService.removeRoleFromUser(user, AvailableRole.ROLE_ADMIN);
        return ResponseEntity.status(HttpStatus.OK).body(user.getRoles().toString());
    }

    @PutMapping("/addRestaurantOwnerRole/{userId}")
    @RolesAllowed({"ROLE_SUPER_ADMIN","ROLE_ADMIN"})
    public ResponseEntity<String> addRestaurantOwnerRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User not found by email"));
        roleService.addRoleToUser(user, AvailableRole.ROLE_RESTAURANT_OWNER);
        return ResponseEntity.status(HttpStatus.OK).body(user.getRoles().toString());
    }

    @PutMapping("/removeRestaurantOwnerRole/{userId}")
    @RolesAllowed({"ROLE_SUPER_ADMIN","ROLE_ADMIN"})
    public ResponseEntity<String> removeRestaurantOwnerRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()->new CustomException("User not found by email"));
        roleService.removeRoleFromUser(user, AvailableRole.ROLE_RESTAURANT_OWNER);
        return ResponseEntity.status(HttpStatus.OK).body(user.getRoles().toString());
    }
}
