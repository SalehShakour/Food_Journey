package com.foodjou.fjapp.services;
import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public Role getRoleById(String id) {
        return roleRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public void deleteRoleById(String id) {
        roleRepository.deleteById(Long.valueOf(id));
    }

    public void updateRoleById(String id, Role updatedRole) {
        Role existingRole = roleRepository.findById(Long.valueOf(id)).orElse(null);
        if (existingRole != null) {
            existingRole.setRoleName(updatedRole.getRoleName());
            existingRole.setDescription(updatedRole.getDescription());
            roleRepository.save(existingRole);
        }
    }
}

