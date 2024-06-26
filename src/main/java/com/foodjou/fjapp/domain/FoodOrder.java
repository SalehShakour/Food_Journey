package com.foodjou.fjapp.domain;

import com.foodjou.fjapp.domain.log.LoggingListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "food_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(LoggingListener.class)
public class FoodOrder {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return "FoodOrder{" +
                "id=" + id +
                ", food=" + food.getFoodName() +
                ", order=" + order.getId() +
                ", quantity=" + quantity +
                ", date time='" + new Date() +
                '}';
    }

}
