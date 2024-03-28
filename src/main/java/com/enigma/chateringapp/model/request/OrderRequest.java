package com.enigma.chateringapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetail;

}
