package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.myEnum.AvailableRole;
import com.foodjou.fjapp.services.RoleService;
import com.foodjou.fjapp.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AdminController {
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PutMapping("/admin/add/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public void addAdminRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new CustomException("User not found by email"));
        roleService.addRoleToUser(user, AvailableRole.ROLE_ADMIN);
        logger.info("userId:" + userId + " " + user.getRoles().toString());
    }

    @PutMapping("/admin/remove/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public void removeAdminRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new CustomException("User not found by email"));
        roleService.removeRoleFromUser(user, AvailableRole.ROLE_ADMIN);
        logger.info("userId:" + userId + " " + user.getRoles().toString());
    }

    @PutMapping("/owner/add/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public void addRestaurantOwnerRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new CustomException("User not found by Id"));
        roleService.addRoleToUser(user, AvailableRole.ROLE_RESTAURANT_OWNER);
        logger.info("userId:" + userId + " " + user.getRoles().toString());
    }

    @PutMapping("/owner/remove/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public void removeRestaurantOwnerRole(@PathVariable String userId){
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(()->new CustomException("User not found by email"));
        roleService.removeRoleFromUser(user, AvailableRole.ROLE_RESTAURANT_OWNER);
        logger.info("userId:" + userId + " " + user.getRoles().toString());
    }

    @GetMapping("/admin/users/all")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<String> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser().toString());
    }
}
