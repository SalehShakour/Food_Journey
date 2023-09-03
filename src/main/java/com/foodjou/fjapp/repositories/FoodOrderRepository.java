package com.foodjou.fjapp.repositories;

import com.foodjou.fjapp.domain.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

    @Query("""
            SELECT o.id ,f.foodName, fo.quantity, u.firstname, u.lastname, u.phoneNumber, u.address
            FROM FoodOrder fo
            JOIN Food f ON fo.food.id = f.id
            JOIN Order o ON fo.order.id = o.id
            JOIN User u ON o.user.id = u.id
            WHERE f.restaurant.id = :restaurantId
            """)
    List<String> findDistinctFoodNamesByRestaurantId(@Param("restaurantId") Long restaurantId);
}
