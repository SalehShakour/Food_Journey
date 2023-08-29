package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MapStructUser mapStructUser;

    public User userValidation(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("User not found"));
    }

    public UserDTO getUserById(String id) {
        return mapStructUser.userToUserDTO(userValidation(id));
    }

    public void deleteUserById(String id) {
        userRepository.delete(userValidation(id));
    }

    public void updateUserById(String id, UserDTO updatedUserDTO) {
        User existingUser = userValidation(id);
        mapStructUser.updateUserDtoToUser(updatedUserDTO, existingUser);
        userRepository.save(existingUser);
    }
    public void getAllUser(){

    }

}
