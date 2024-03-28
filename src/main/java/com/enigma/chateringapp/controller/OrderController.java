package com.enigma.chateringapp.controller;

import com.enigma.chateringapp.model.request.OrderRequest;
import com.enigma.chateringapp.model.response.CommonResponse;
import com.enigma.chateringapp.model.response.OrderResponse;
import com.enigma.chateringapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    private ResponseEntity<?> create(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.create(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully Created New Transaction")
                        .data(orderResponse)
                        .build());
    }
}
