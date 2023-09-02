package com.foodjou.fjapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foodjou.fjapp.domain.log.LoggingListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "restaurants")
@EntityListeners(LoggingListener.class)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "address")
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    //todo why with cascade delete not working ?
    @JsonManagedReference
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private List<Food> foods;

    @Transient
    private Double averagePriceFood;

    public Double getAveragePriceFood() {
        if (foods == null || foods.isEmpty()) {
            return 0.0;
        }
        return foods.stream()
                .filter(food -> food.getPrice() != null)
                .mapToDouble(Food::getPrice)
                .average()
                .orElse(0.0);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", date time='" + new Date() +
                '}';
    }
}

