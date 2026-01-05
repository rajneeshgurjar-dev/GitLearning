package com.example.zoom.repository;

import com.example.zoom.dto.UserWithOrdersDto;

public interface UserAggregationRepository {

    UserWithOrdersDto getUserWithOrders(String userId);
}
