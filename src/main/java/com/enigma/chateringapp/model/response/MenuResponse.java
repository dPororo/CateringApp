package com.enigma.chateringapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private String menuId;
    private String name;
    private String description;
    private Long price;
    private Integer stock;
}
