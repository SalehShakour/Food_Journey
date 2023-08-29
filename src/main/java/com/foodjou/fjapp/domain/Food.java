package com.foodjou.fjapp.domain;
import com.foodjou.fjapp.domain.log.LoggingListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "foods")
@EntityListeners(LoggingListener.class)
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
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", date time='" + new Date() +
                '}';
    }
}
