package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRole;
import com.foodjou.fjapp.services.entityService.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InitializationService {
    private final RoleService roleService;
    private final MapStructRole mapStructRole;

    public InitializationService(RoleService roleService, MapStructRole mapStructRole) {
        this.roleService = roleService;
        this.mapStructRole = mapStructRole;
    }

    @PostConstruct
    public void initializeRole() {
        Long id = roleService.getRoleIdWithName("Customer");
        if (id == -1L) {
            Role role = new Role();
            role.setRoleName("Customer");
            role.setDescription("Regular client");
            roleService.addRole(mapStructRole.roleToRoleDTO(role));
        }
    }
}
