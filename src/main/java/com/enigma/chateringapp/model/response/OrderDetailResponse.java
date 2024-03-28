package com.enigma.chateringapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private String orderDetailId;
    private MenuResponse menuResponse;
    private Integer quantity;
}
