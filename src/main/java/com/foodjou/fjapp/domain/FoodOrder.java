package com.foodjou.fjapp.domain;

import com.foodjou.fjapp.domain.log.LoggingListener;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "quantity")
    private int quantity;

    @Override
    public String toString() {
        return "FoodOrder{" +
                "id=" + id +
                ", food=" + food +
                ", order=" + order +
                ", quantity=" + quantity +
                '}';
    }

}
