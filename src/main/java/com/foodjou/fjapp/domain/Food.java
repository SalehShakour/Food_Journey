package com.foodjou.fjapp.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food")
public class Food {
    @Id
    private long id;
    private String food_name;
    private double price;
    private String description;
}
