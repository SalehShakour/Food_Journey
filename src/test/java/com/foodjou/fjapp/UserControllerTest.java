package com.foodjou.fjapp;

import com.foodjou.fjapp.config.JwtService;
import com.foodjou.fjapp.controllers.UserController;
import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.UserDTO;
import com.foodjou.fjapp.mapper.entityMapper.MapStructUser;
import com.foodjou.fjapp.repositories.UserRepository;
import com.foodjou.fjapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String ROLE_USER = "ROLE_USER";
    private static final String USER_EMAIL = "test@email";
    private static final String USER_FIRSTNAME = "saleh";
    private static final String USER_LASTNAME = "shakour";
    private static final String USER_PHONE = "0000";
    private static final String USER_ADDRESS = "home";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapStructUser mapStructUser;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        User user = createUser();
        UserDTO userDTO = createUserDTO();

        Mockito.when(mapStructUser.userToUserDTO(user)).thenReturn(userDTO);
        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Test
    public void showProfileTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(USER_FIRSTNAME))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(USER_EMAIL))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(USER_ADDRESS));
    }

    private User createUser() {
        User user = new User(1L, USER_EMAIL, new HashSet<>(), USER_FIRSTNAME,
                USER_LASTNAME, USER_PHONE, "0", USER_ADDRESS, new ArrayList<>(), new ArrayList<>());
        Set<Role> userRoles = user.getRoles();
        userRoles.add(new Role(1L, ROLE_USER, user));
        user.setRoles(userRoles);
        return user;
    }

    private UserDTO createUserDTO() {
        return new UserDTO(USER_EMAIL, USER_FIRSTNAME, USER_LASTNAME, "0", USER_ADDRESS);
    }

}

