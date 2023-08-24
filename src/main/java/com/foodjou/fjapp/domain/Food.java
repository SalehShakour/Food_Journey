package com.foodjou.fjapp.domain;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "food_name")
    private String foodName;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "restaurant_id") // This refers to the foreign key in the Restaurant table
    private Restaurant restaurant;
}
