package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user_table")
public class User {
    @Id
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String phone_number;
    private String address;
    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "owner")
    private List<Restaurant> ownedRestaurants;
}
