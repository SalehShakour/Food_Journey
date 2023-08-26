package com.foodjou.fjapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Food> foods = new ArrayList<>();

}
