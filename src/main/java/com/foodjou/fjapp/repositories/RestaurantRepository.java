package com.foodjou.fjapp.repositories;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long>, JpaSpecificationExecutor<Restaurant> {
    Optional<List<Restaurant>> findByOwner(User user);
    List<Restaurant> findAll(Specification<Restaurant> spec);
}
