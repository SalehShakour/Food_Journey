package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant {
    @Id
    private long id;
    private String restaurant_name;
    private String address;
    private String phone_number;
    @ManyToOne
    private User owner;
}
