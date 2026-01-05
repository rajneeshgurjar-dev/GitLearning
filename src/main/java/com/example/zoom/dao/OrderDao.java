package com.example.zoom.dao;

import com.example.zoom.dto.OrderRequestDto;
import com.example.zoom.entity.Order;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDao {

    Order save(Order order);

//    List<Order> findByUserId(String userId);

    void deleteByUserId(String userId);

    Page<Order> findByUserId(ObjectId userId, Pageable pageable);

    void partialUpdate(String orderId, OrderRequestDto dto);
}