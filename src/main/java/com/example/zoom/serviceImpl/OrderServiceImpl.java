package com.example.zoom.serviceImpl;

import com.example.zoom.dao.OrderDao;
import com.example.zoom.dao.UserDao;
import com.example.zoom.dto.OrderRequestDto;
import com.example.zoom.dto.OrderResponseDto;
import com.example.zoom.entity.Order;
import com.example.zoom.exception.BadRequestException;
import com.example.zoom.exception.ResourceNotFoundException;
import com.example.zoom.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;

    @Override
    public OrderResponseDto createOrder(String userId, OrderRequestDto dto) {

        userDao.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        Order order = new Order();
        order.setUserId(new ObjectId(userId));
        order.setProductName(dto.getProductName());
        order.setAmount(dto.getAmount());
        order.setOrderedAt(LocalDateTime.now());
        return mapToResponse(orderDao.save(order));
    }

//    @Override
//    public List<OrderResponseDto> getOrdersByUserId(String userId) {
//
//        return orderDao.findByUserId(userId)
//                .stream()
//                .map(this::mapToResponse)
//                .collect(Collectors.toList());
//    }

    @Override
    public Page<OrderResponseDto> getOrdersByUser(
            String userId,
            int page,
            int size,
            String sortBy,
            String direction) {

        ObjectId objectId = new ObjectId(userId);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return orderDao.findByUserId(objectId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public void updateOrderPartially(String orderId, OrderRequestDto dto) {

        if (dto.getProductName() == null && dto.getAmount() == 0) {
            throw new BadRequestException("No fields provided for update");
        }

        orderDao.partialUpdate(orderId, dto);
    }

    private OrderResponseDto mapToResponse(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUserId(),
                order.getProductName(),
                order.getAmount(),
                order.getOrderedAt()
        );
    }
}
