package com.enigma.chateringapp.service;

import com.enigma.chateringapp.model.entity.Menu;
import com.enigma.chateringapp.model.request.MenuRequest;
import com.enigma.chateringapp.model.response.MenuResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MenuService {
    MenuResponse create(MenuRequest menuRequest);
    List<Menu> getAll();
    Page<MenuResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size);
    MenuResponse update(MenuRequest menuRequest);
    MenuResponse getById(String id);
    void delete(String id);


}
