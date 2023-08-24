package com.foodjou.fjapp.mapper.entityMapper;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapStructUser {
    User userDtoToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
    void updateUserDtoToUser(UserDTO userDTO, @MappingTarget User user);
}
