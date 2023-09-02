package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.Restaurant;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.RestaurantResponseDTO;
import com.foodjou.fjapp.myEnum.OrderStatus;
import com.foodjou.fjapp.services.FoodOrderService;
import com.foodjou.fjapp.services.FoodService;
import com.foodjou.fjapp.services.OrderService;
import com.foodjou.fjapp.services.RestaurantService;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
@RolesAllowed({"ROLE_ADMIN", "ROLE_RESTAURANT_OWNER", "ROLE_SUPER_ADMIN"})
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final FoodOrderService foodOrderService;

    public boolean hasAccessToRestaurant(String id, User currentUser) {
        return currentUser.hasAnyRoles(new HashSet<>(List.of("ROLE_ADMIN", "ROLE_SUPER_ADMIN"))) ||
                Long.valueOf(id).equals(restaurantService.getRestaurantOwner(currentUser).getId());
    }


    @PostMapping
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO,
                                                @AuthenticationPrincipal User currentUser) {
        restaurantService.addRestaurant(currentUser, restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
    }


    @RolesAllowed("ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String id) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurantByID(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurantById(@PathVariable String id,
                                                       @Valid @RequestBody RestaurantDTO updatedRestaurantDTO) {
        restaurantService.updateRestaurant(id, updatedRestaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant updated successfully");
    }

    @GetMapping("/{id}/menu")
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<List<Food>> getRestaurantMenuById(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false) String search) {
        List<Food> menu = restaurantService.getMenu(id);

        if (search != null && !search.isEmpty()) {
            menu = menu.stream()
                    .filter(food -> food.getFoodName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if ("desc".equalsIgnoreCase(sort)) {
            menu.sort(Comparator.comparingDouble(Food::getPrice).reversed());
        } else {
            menu.sort(Comparator.comparingDouble(Food::getPrice));
        }

        return ResponseEntity.status(HttpStatus.OK).body(menu);
    }



    @GetMapping("/{id}/orders")
    public ResponseEntity<List<String>> getAllOrder(@PathVariable String id,
                                                    @AuthenticationPrincipal User currentUser) {
        if (hasAccessToRestaurant(id, currentUser)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(foodOrderService.getFoodOrdersByRestaurantId(Long.valueOf(id)));
        } else return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
    @PutMapping("{id}/orders/{orderId}/status")
    public ResponseEntity<String> changeOrderStatus(@PathVariable String id,
                                                    @PathVariable String orderId,
                                                    @AuthenticationPrincipal User currentUser,
                                                    @RequestParam OrderStatus newStatus
                                                    ) {
        if (hasAccessToRestaurant(id,currentUser)){
            orderService.changeOrderStatus(orderId, newStatus);
            return ResponseEntity.status(HttpStatus.OK).body("Order status successfully changed to "+newStatus);
        }
        else return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("you can't access orders");
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurantsWithMenu(
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false) String restaurantSearch,
            @RequestParam(required = false) String foodSearch) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        if (restaurantSearch != null && !restaurantSearch.isEmpty()) {
            String restaurantRegexPattern = ".*" + Pattern.quote(restaurantSearch) + ".*";
            restaurants = restaurants.stream()
                    .filter(restaurant -> restaurant.getRestaurantName().toLowerCase().matches(restaurantRegexPattern))
                    .collect(Collectors.toList());
        }

        if ("desc".equalsIgnoreCase(sort)) {
            restaurants.sort(Comparator.comparingDouble(Restaurant::getAveragePriceFood).reversed());
        } else {
            restaurants.sort(Comparator.comparingDouble(Restaurant::getAveragePriceFood));
        }

        List<RestaurantResponseDTO> responseDTOs = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            RestaurantResponseDTO responseDTO = new RestaurantResponseDTO();
            responseDTO.setRestaurantId(restaurant.getId());
            responseDTO.setRestaurantName(restaurant.getRestaurantName());

            if (foodSearch != null && !foodSearch.isEmpty()) {
                String foodRegexPattern = ".*" + Pattern.quote(foodSearch) + ".*";
                List<Food> filteredFoods = restaurant.getFoods().stream()
                        .filter(food -> food.getFoodName().toLowerCase().matches(foodRegexPattern))
                        .collect(Collectors.toList());
                responseDTO.setFoods(filteredFoods);
            } else {
                responseDTO.setFoods(restaurant.getFoods());
            }

            responseDTOs.add(responseDTO);
        }

        return ResponseEntity.ok(responseDTOs);
    }


}
