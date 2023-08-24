package com.foodjou.fjapp.services.entityService;

import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRole;
import com.foodjou.fjapp.repositories.RoleRepository;
import com.foodjou.fjapp.dto.entityDTO.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final MapStructRole mapStructRole;

    @Autowired
    public RoleService(RoleRepository roleRepository, MapStructRole mapStructRole) {
        this.roleRepository = roleRepository;
        this.mapStructRole = mapStructRole;
    }

    public void addRole(RoleDTO roleDTO) {
        roleRepository.save(mapStructRole.roleDtoToRole(roleDTO));
    }

    public RoleDTO getRoleById(String id) {
        Role role = roleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("Role not found"));
        return mapStructRole.roleToRoleDTO(role);
    }

    public void deleteRoleById(String id) {
        Role role = roleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("Role not found"));
        roleRepository.delete(role);
    }

    public void updateRoleById(String id, RoleDTO updatedRoleDTO) {
        Role existingRole = roleRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("Role not found"));
        existingRole = mapStructRole.updateRoleDtoToRole(updatedRoleDTO, existingRole);
        roleRepository.save(existingRole);
    }
    public Long getRoleIdWithName(String roleName) {
        List<Role> roles = roleRepository.findByRoleName(roleName);
        if (roles.isEmpty()) return -1L;
        else return roles.get(0).getId();
    }
}

