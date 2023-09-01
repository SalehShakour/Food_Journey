package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MapStructUser mapStructUser;

    public User userValidation(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException("User not found"));
    }

    public User getUserById(String id) {
        return userValidation(id);
    }
    public void deleteUserById(String id) {
        userRepository.delete(userValidation(id));
    }

    public void updateUserById(User currentUser, UserDTO updatedUserDTO) {
        mapStructUser.updateUserDtoToUser(updatedUserDTO, currentUser);
        userRepository.save(currentUser);
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

}
