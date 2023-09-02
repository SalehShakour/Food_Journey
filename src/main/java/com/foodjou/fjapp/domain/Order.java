package com.foodjou.fjapp.domain;

import com.foodjou.fjapp.converter.OrderStatusConverter;
import com.foodjou.fjapp.domain.log.LoggingListener;
import com.foodjou.fjapp.myEnum.OrderStatus;
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

    @Transient
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Convert(converter = OrderStatusConverter.class)
    @Column(name = "status")
    private OrderStatus status;

    public Double getTotalPrice() {
        if (foodOrders != null && !foodOrders.isEmpty()) {
            return foodOrders.stream()
                    .mapToDouble(foodOrder -> foodOrder.getQuantity() * foodOrder.getFood().getPrice())
                    .sum();
        }
        return 0.0;
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", date time='" + new Date() +
                '}';
    }

}
