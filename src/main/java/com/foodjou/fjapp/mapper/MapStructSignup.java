package com.foodjou.fjapp.mapper;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.SignUpRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MapStructSignup {
    User signupDtoToUser(SignUpRequestDTO userDTO);
    SignUpRequestDTO UserToSignupDto(User user);
    void updateSignupDtoToUser(SignUpRequestDTO userDTO, @MappingTarget User user);
}


