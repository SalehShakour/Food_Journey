package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "food_order")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrder {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "food_id",nullable = false)
    private Food food;
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

}
