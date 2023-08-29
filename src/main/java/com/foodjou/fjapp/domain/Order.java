package com.foodjou.fjapp.domain;

import com.foodjou.fjapp.domain.log.LoggingListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(LoggingListener.class)
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<FoodOrder> foodOrders = new ArrayList<>();

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", foodOrders=" + foodOrders +
                ", date time='" + new Date() +
                '}';
    }

}
