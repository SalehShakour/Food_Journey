package com.foodjou.fjapp.repositories;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.FoodDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("select new com.foodjou.fjapp.dto.entityDTO.FoodDTO(f.restaurant.id,f.id, f.foodName, f.price, f.description) from Food f join f.restaurant order by f.restaurant.id")
    <T> List<T> findByRestaurant(Class<T> type);
    List<Food> findAll(Specification<Food> spec);
}
