package com.foodjou.fjapp.mapper;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.SignUpRequestDTO;
import com.foodjou.fjapp.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapStructUser {
    @Mapping(target = "role", ignore = true) // Ignore role mapping for SignUpRequestDTO
    User userDtoToUser(SignUpRequestDTO userDTO);
    UserDTO userToUserDTO(User user);
    void updateUserDtoToUser(UserDTO userDTO, @MappingTarget User user);
}
