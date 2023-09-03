package com.foodjou.fjapp.repositories.specification;

import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecifications {
    public static Specification<Restaurant> searchByFilters(String name, String address) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("restaurantName"), "%" + name + "%"));
            }

            if (address != null && !address.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("address"), "%" + address + "%"));
            }


            return predicate;
        };
    }
}
