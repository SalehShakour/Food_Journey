package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
    //Todo delete comment if unusable
//    @OneToMany(mappedBy = "owner")
//    private List<Restaurant> ownedRestaurants;
}
