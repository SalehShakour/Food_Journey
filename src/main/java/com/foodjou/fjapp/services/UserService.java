package com.foodjou.fjapp.services;

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

    public UserDTO getUserById(String id) {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("User not found"));
        return mapStructUser.userToUserDTO(user);
    }

    public void deleteUserById(String id) {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("User not found"));
        userRepository.delete(user);
    }

    public void updateUserById(String id, UserDTO updatedUserDTO) {
        User existingUser = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("User not found"));
        mapStructUser.updateUserDtoToUser(updatedUserDTO, existingUser);
        userRepository.save(existingUser);
    }

}
