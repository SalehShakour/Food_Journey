package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @ManyToOne
    private User owner;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Food> foods;

}

