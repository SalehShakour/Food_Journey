package com.foodjou.fjapp.repositories;
import com.foodjou.fjapp.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    @Query("select new com.foodjou.fjapp.dto.entityDTO.FoodDTO(f.id, f.foodName, f.price, f.description) from Food f join f.restaurant where f.restaurant.id= :id")
    <T> List<T>findByRestaurant(@Param("id") Long id, Class<T> type);

}
