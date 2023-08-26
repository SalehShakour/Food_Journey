package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.repositories.RoleRepository;
import com.foodjou.fjapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository,
                       UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void addRoleToUser(User user, AvailableRole role) {
        if (!user.hasRole(role.name())){
            Role newRole = new Role(role.name());
            roleRepository.save(newRole);
            user.addRole(newRole);
            userRepository.save(user);
        }


    }

    public void removeRoleFromUser(User user, AvailableRole role) {
        Long id = user.getRoleId(role.name());
        if (id != -1L){
            user.removeRole(id);
            roleRepository.deleteById(id);
            userRepository.save(user);
        }
    }

}

