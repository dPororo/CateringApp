package com.enigma.chateringapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailRequest {
    private String menuPriceId;
    private Integer quantity;

}
