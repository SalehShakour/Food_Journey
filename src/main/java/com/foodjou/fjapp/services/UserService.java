package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (userRepository.findByEmail(updatedUserDTO.email()).orElse(null) == null){
            mapStructUser.updateUserDtoToUser(updatedUserDTO, currentUser);
            userRepository.save(currentUser);
        }
        else throw new CustomException("This email address is unavailable");
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Restaurant getRestaurantById(User currentUser, Long restaurantId){
        return currentUser.getOwnedRestaurants().stream()
                .filter(restaurant -> restaurant.getId()==restaurantId)
                .findFirst().orElseThrow(
                        ()->new CustomException("Current user don't have restaurant with this id"));
    }

}
