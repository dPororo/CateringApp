package com.enigma.chateringapp.service;

import com.enigma.chateringapp.model.request.OrderDetailRequest;
import com.enigma.chateringapp.model.response.OrderDetailResponse;

public interface OrderDetailService {
    OrderDetailResponse create(OrderDetailRequest orderDetailRequest);
    OrderDetailResponse getById(String id);
}
