package com.enigma.chateringapp.service;

import com.enigma.chateringapp.model.request.OrderRequest;
import com.enigma.chateringapp.model.response.OrderResponse;

public interface OrderService {
    OrderResponse create(OrderRequest orderRequest);
    OrderResponse getById(String id);
}
