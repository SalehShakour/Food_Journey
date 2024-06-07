package com.foodjou.fjapp.repositories.specification;

import com.foodjou.fjapp.domain.Food;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class FoodSpecification {
    public static Specification<Food> searchByFilters(String name, String firstPrice, String type, String secondPrice) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("foodName"), "%" + name + "%"));
            }

            if (firstPrice != null && !firstPrice.isEmpty() && type.equals("Between") && secondPrice != null && !secondPrice.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("price"), Long.valueOf(firstPrice), Long.valueOf(secondPrice)));
            } else if (firstPrice != null && !firstPrice.isEmpty() && type.equals("Less")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), Long.valueOf(firstPrice)));
            } else if (firstPrice != null && !firstPrice.isEmpty() && type.equals("Greater")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Long.valueOf(firstPrice)));
            }
            return predicate;
        };
    }
}
