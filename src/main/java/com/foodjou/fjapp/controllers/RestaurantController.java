package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Food;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.RestaurantResponseDTO;
import com.foodjou.fjapp.myEnum.OrderStatus;
import com.foodjou.fjapp.services.FoodOrderService;
import com.foodjou.fjapp.services.OrderService;
import com.foodjou.fjapp.services.RestaurantService;
import com.foodjou.fjapp.dto.entityDTO.RestaurantDTO;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_RESTAURANT_OWNER', 'ROLE_SUPER_ADMIN')")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final FoodOrderService foodOrderService;


    @PostMapping
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO,
                                                @AuthenticationPrincipal User currentUser) {
        restaurantService.addRestaurant(currentUser, restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant created successfully");
    }

    @PutMapping("/{restaurantId:[0-9]+}")
    public ResponseEntity<String> updateRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO,
                                                   @AuthenticationPrincipal User currentUser,
                                                   @PathVariable Long restaurantId) {
        restaurantService.updateRestaurant(restaurantId,currentUser, restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Restaurant updated successfully");
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String restaurantId) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurantByID(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
    }

    @GetMapping("/{id}/menu")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<Food>> getRestaurantMenuById(@PathVariable String id) {
        List<Food> menu = restaurantService.getMenu(id);
        return ResponseEntity.status(HttpStatus.OK).body(menu);
    }

    @GetMapping("/{restaurantId:[0-9]+}/orders")
    public ResponseEntity<List<String>> getAllOrder(@PathVariable Long restaurantId,
                                                    @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(foodOrderService.getFoodOrdersByRestaurantId(currentUser, restaurantId));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<String> changeOrderStatus(@PathVariable String orderId,
                                                    @AuthenticationPrincipal User currentUser,
                                                    @RequestParam OrderStatus newStatus
    ) {
        restaurantService.findRestaurantsByOwner(currentUser);

        orderService.changeOrderStatus(currentUser, orderId, newStatus);
        return ResponseEntity.status(HttpStatus.OK).body("Order status successfully changed to " + newStatus);

    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurantsWithMenu() {
        return ResponseEntity.ok(restaurantService.getAllRestaurantsWithMenu());
    }
}
