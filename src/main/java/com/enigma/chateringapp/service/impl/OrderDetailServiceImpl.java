package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.model.entity.MenuPrice;
import com.enigma.chateringapp.model.entity.Order;
import com.enigma.chateringapp.model.entity.OrderDetail;
import com.enigma.chateringapp.model.request.OrderDetailRequest;
import com.enigma.chateringapp.model.response.MenuResponse;
import com.enigma.chateringapp.model.response.OrderDetailResponse;
import com.enigma.chateringapp.repository.MenuPriceRepository;
import com.enigma.chateringapp.repository.OrderDetailRepository;
import com.enigma.chateringapp.repository.OrderRepository;
import com.enigma.chateringapp.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final MenuPriceRepository menuPriceRepository;

    @Override
    public OrderDetailResponse create(OrderDetailRequest orderDetailRequest) {
        Order order = orderRepository.findById(orderDetailRequest.getMenuPriceId()).orElse(null);
        MenuPrice menuPrice = menuPriceRepository.findById(orderDetailRequest.getMenuPriceId()).orElse(null);

        if (order == null || menuPrice == null) {
            return null;
        }

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .menuPrice(menuPrice)
                .quantity(orderDetailRequest.getQuantity())
                .build();

        MenuResponse menuResponse = MenuResponse.builder()
                .menuId(menuPrice.getMenu().getId())
                .name(menuPrice.getMenu().getName())
                .description(menuPrice.getMenu().getDescription())
                .price(menuPrice.getPrice())
                .stock(menuPrice.getStock() - orderDetailRequest.getQuantity())
                .build();

        return OrderDetailResponse.builder()
                .orderDetailId(orderDetail.getId())
                .menuResponse(menuResponse)
                .quantity(orderDetail.getQuantity())
                .build();
    }

    @Override
    public OrderDetailResponse getById(String id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .menuResponse(MenuResponse.builder()
                            .menuId(orderDetail.getMenuPrice().getMenu().getId())
                            .name(orderDetail.getMenuPrice().getMenu().getName())
                            .description(orderDetail.getMenuPrice().getMenu().getDescription())
                            .price(orderDetail.getMenuPrice().getPrice())
                            .stock(orderDetail.getMenuPrice().getStock())
                            .build())
                    .quantity(orderDetail.getQuantity())
                    .build();
        }
        return null;
    }
}
