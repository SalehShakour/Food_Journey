package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.services.RoleService;
import com.foodjou.fjapp.dto.RoleDTO;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> addRole(@Valid @RequestBody RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Role created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable String id) {

        roleService.deleteRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Role deleted successfully");

    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRoleById(@PathVariable String id,@Valid @RequestBody RoleDTO updatedRoleDTO) {
        roleService.updateRoleById(id, updatedRoleDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Role updated successfully");

    }
}
