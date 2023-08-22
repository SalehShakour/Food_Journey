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
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "food_name")
    private String foodName;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
}
