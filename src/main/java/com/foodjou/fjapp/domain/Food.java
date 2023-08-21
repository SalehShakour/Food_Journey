package com.foodjou.fjapp.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "food")
public class Food {
    @Id
    private long id;
    private String food_name;
    private double price;
    private String description;
}
