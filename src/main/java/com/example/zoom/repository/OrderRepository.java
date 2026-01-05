package com.example.zoom.repository;

import com.example.zoom.entity.Order;
import org.bson.types.ObjectId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);
    void deleteByUserId(String userId);
    Page<Order> findByUserId(ObjectId userId, Pageable pageable);
}