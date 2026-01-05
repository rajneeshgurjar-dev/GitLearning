package com.example.zoom.service;

import com.example.zoom.dto.OrderRequestDto;
import com.example.zoom.dto.OrderResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(String userId, OrderRequestDto dto);

//    List<OrderResponseDto> getOrdersByUserId(String userId);

    Page<OrderResponseDto> getOrdersByUser(
            String userId,
            int page,
            int size,
            String sortBy,
            String direction
    );

    void updateOrderPartially(String orderId, OrderRequestDto dto);
}
