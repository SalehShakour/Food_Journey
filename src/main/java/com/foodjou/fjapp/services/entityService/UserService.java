package com.foodjou.fjapp.services.entityService;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.mapper.entityMapper.MapStructRole;
import com.foodjou.fjapp.mapper.MapStructSignup;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.dto.SignUpRequestDTO;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MapStructRole mapStructRole;
    private final MapStructSignup mapStructSignup;
    private final MapStructUser mapStructUser;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, MapStructRole mapStructRole, MapStructSignup mapStructSignup, MapStructUser mapStructUser) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapStructRole = mapStructRole;
        this.mapStructSignup = mapStructSignup;
        this.mapStructUser = mapStructUser;
    }

    public void signUpUser(SignUpRequestDTO signUpRequestDTO) {
        User user = mapStructSignup.signupDtoToUser(signUpRequestDTO);
        userRepository.save(user);
    }

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("User not found"));
        return mapStructUser.userToUserDTO(user);
    }

    public void deleteUserById(String id) {
        User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new CustomException("User not found"));
        userRepository.delete(user);
    }

    public void updateUserById(String id, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("User not found"));
        mapStructUser.updateUserDtoToUser(updatedUserDTO, existingUser);
        userRepository.save(existingUser);
    }
}
