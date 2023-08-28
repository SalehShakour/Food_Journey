package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "food_order")
public class FoodOrder {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
