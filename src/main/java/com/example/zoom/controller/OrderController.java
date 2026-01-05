package com.example.zoom.controller;

import com.example.zoom.dto.OrderRequestDto;
import com.example.zoom.dto.OrderResponseDto;
import com.example.zoom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@PathVariable String userId, @RequestBody OrderRequestDto dto) {
        return orderService.createOrder(userId, dto);
    }

//    @GetMapping
//    public List<OrderResponseDto> getOrders(@PathVariable String userId) {
//        return orderService.getOrdersByUserId(userId);
//    }

    @GetMapping
    public Page<OrderResponseDto> getOrders(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "orderedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return orderService.getOrdersByUser(
                userId, page, size, sortBy, direction);
    }

    @PatchMapping("/{orderId}")
    public String updateOrderPartially(
            @PathVariable String orderId,
            @RequestBody OrderRequestDto dto) {

        orderService.updateOrderPartially(orderId, dto);
        return "Order updated successfully";
    }

}

