package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
