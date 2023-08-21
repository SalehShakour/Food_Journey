package com.foodjou.fjapp.repositories;
import com.foodjou.fjapp.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FoodRepository extends JpaRepository<Food,Long> {
}
