package com.foodjou.fjapp.controllers;

import com.foodjou.fjapp.domain.User;
import com.foodjou.fjapp.dto.entityDTO.OrderDTO;
import com.foodjou.fjapp.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<String> createOrder(@AuthenticationPrincipal User currentUser, @RequestBody List<OrderDTO> orderDTOList) {
        orderService.createOrder(currentUser,orderDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order submitted");
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> removeOrder(@AuthenticationPrincipal User currentUser,@PathVariable String orderId){
        orderService.removeOrder(currentUser, orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Order removed");
    }

}
