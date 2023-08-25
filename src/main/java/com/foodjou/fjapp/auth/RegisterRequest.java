package com.foodjou.fjapp.auth;


import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.Role;
import com.foodjou.fjapp.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private RoleRepository roleRepository;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private Set<Role> roles = new HashSet<>();
    private List<Order> orders = new ArrayList<>();
}
