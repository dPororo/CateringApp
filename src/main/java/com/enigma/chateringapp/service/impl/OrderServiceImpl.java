package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.model.entity.Customer;
import com.enigma.chateringapp.model.entity.MenuPrice;
import com.enigma.chateringapp.model.entity.Order;
import com.enigma.chateringapp.model.entity.OrderDetail;
import com.enigma.chateringapp.model.request.OrderRequest;
import com.enigma.chateringapp.model.response.CustomerResponse;
import com.enigma.chateringapp.model.response.MenuResponse;
import com.enigma.chateringapp.model.response.OrderDetailResponse;
import com.enigma.chateringapp.model.response.OrderResponse;
import com.enigma.chateringapp.repository.OrderRepository;
import com.enigma.chateringapp.service.CustomerService;
import com.enigma.chateringapp.service.MenuPriceService;
import com.enigma.chateringapp.service.OrderService;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final MenuPriceService menuPriceService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public OrderResponse create(OrderRequest orderRequest) {
        CustomerResponse customerResponse = customerService.getById(orderRequest.getCustomerId());
        List<OrderDetail> orderDetails = orderRequest.getOrderDetail().stream().map(orderDetailRequest -> {
            MenuPrice menuPrice = menuPriceService.getById(orderDetailRequest.getMenuPriceId());
            return OrderDetail.builder()
                    .menuPrice(menuPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
        }).toList();

        Customer customer = Customer.builder()
                .id(customerResponse.getId())
                .name(customerResponse.getName())
                .phone(customerResponse.getPhone())
                .address(customerResponse.getAddress())
                .build();

        Order order = Order.builder()
                .customer(customer)
                .orderDetails(orderDetails)
                .transDate(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(orderDetail -> {
            orderDetail.setOrder(order);

            MenuPrice currentMenuPrice = orderDetail.getMenuPrice();

            currentMenuPrice.setStock(currentMenuPrice.getStock() - orderDetail.getQuantity());

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
        }).toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerResponse(customerResponse)
                .orderDetails(orderDetailResponses)
                .transDate(order.getTransDate())
                .build();
    }

    @Override
    public OrderResponse getById(String id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null){
            return OrderResponse.builder()
                    .orderId(order.getId())
                    .customerResponse(customerService.getById(order.getCustomer().getId()))
                    .orderDetails(order.getOrderDetails().stream().map(orderDetail -> OrderDetailResponse.builder()
                            .orderDetailId(orderDetail.getId())
                            .quantity(orderDetail.getQuantity())
                            .menuResponse(MenuResponse.builder()
                                    .menuId(orderDetail.getMenuPrice().getMenu().getId())
                                    .name(orderDetail.getMenuPrice().getMenu().getName())
                                    .description(orderDetail.getMenuPrice().getMenu().getDescription())
                                    .build())
                            .build()).toList())
                    .build();
        }
        return null;
    }
}
