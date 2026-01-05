package com.example.zoom.daoImpl;

import com.example.zoom.dao.OrderDao;
import com.example.zoom.dto.OrderRequestDto;
import com.example.zoom.entity.Order;
import com.example.zoom.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

//    @Override
//    public List<Order> findByUserId(String userId) {
//
//        return orderRepository.findByUserId(userId);
//    }

    @Override
    public void deleteByUserId(String userId) {

        orderRepository.deleteByUserId(userId);
    }
    @Override
    public Page<Order> findByUserId(ObjectId userId, Pageable pageable) {

        return orderRepository.findByUserId(userId, pageable);
    }

    @Override
    public void partialUpdate(String orderId, OrderRequestDto dto) {

        Query query = new Query(
                Criteria.where("_id").is(orderId)
        );

        Update update = new Update();

        if (dto.getProductName() != null) {
            update.set("productName", dto.getProductName());
        }

        if (dto.getAmount() != 0) {
            update.set("amount", dto.getAmount());
        }

        if (update.getUpdateObject().isEmpty()) {
            return;
        }

        mongoTemplate.updateFirst(query, update, Order.class);
    }
}
