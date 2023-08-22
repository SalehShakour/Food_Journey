package com.foodjou.fjapp.services;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(String id) {
        return userRepository.findById(Long.valueOf(id)).orElse(null);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(Long.valueOf(id));
    }

    public void updateUserById(String id, User updatedUser) {
        User existingUser = userRepository.findById(Long.valueOf(id)).orElse(null);
        if (existingUser != null) {
            if (updatedUser.getUsername() != null) existingUser.setUsername(updatedUser.getUsername());
            if (updatedUser.getFirstname() != null) existingUser.setFirstname(updatedUser.getFirstname());
            if (updatedUser.getLastname() != null) existingUser.setLastname(updatedUser.getLastname());
            if (updatedUser.getPassword() != null) existingUser.setPassword(updatedUser.getPassword());
            if (updatedUser.getAddress() != null) existingUser.setAddress(updatedUser.getAddress());
            if (updatedUser.getPhoneNumber() != null) existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            if (updatedUser.getRole() != null) existingUser.setRole(updatedUser.getRole());

        }
    }
}
