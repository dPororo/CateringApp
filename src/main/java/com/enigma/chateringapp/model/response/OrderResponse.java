package com.enigma.chateringapp.model.response;

import com.enigma.chateringapp.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private LocalDateTime transDate;
    private CustomerResponse customerResponse;
    private List<OrderDetailResponse> orderDetails;


}
