package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.Order;
import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.OrderDTO;
import com.foodjou.fjapp.exception.CustomException;
import com.foodjou.fjapp.myEnum.OrderStatus;
import com.foodjou.fjapp.services.OrderService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<String> createOrder(@AuthenticationPrincipal User currentUser, @RequestBody List<OrderDTO> orderDTOList) {
        if (orderDTOList==null || orderDTOList.isEmpty()){
            throw new CustomException("Order list is empty or null");
        }
        orderService.createOrder(currentUser,orderDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order submitted");
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderStatus(@AuthenticationPrincipal User currentUser, @PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        if (!Objects.equals(currentUser.getId(), order.getUser().getId())) {
            throw new CustomException("You can't access this order");
        }
        OrderStatus orderStatus = order.getStatus();

        String responseMessage = switch (orderStatus) {
            case PENDING -> {
                Double totalPrice = order.getTotalPrice();
                yield "Your order is pending. Please pay " + totalPrice + " to complete it.";
            }
            case PAID ->
                    "Your order has been paid. Please wait until it is delivered.";
            case COMPLETED ->
                    "Your order has been completed. Please rate us.";
            case CANCELED ->
                    "This order has been canceled.";
        };

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
