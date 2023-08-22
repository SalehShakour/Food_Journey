package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.services.RoleService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("")
    public ResponseEntity<String> addRole(@RequestBody Role role) {
        try {
            roleService.addRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role data is not valid");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable String id) {
        try {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.status(HttpStatus.OK).body(role);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable String id) {
        try {
            roleService.deleteRoleById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Role deleted successfully");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRoleById(@PathVariable String id, @RequestBody Role updatedRole) {
        try {
            roleService.updateRoleById(id, updatedRole);
            return ResponseEntity.status(HttpStatus.OK).body("Role updated successfully");
        } catch (EmptyResultDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }
    }
}
