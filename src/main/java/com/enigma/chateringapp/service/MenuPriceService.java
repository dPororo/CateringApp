package com.enigma.chateringapp.service;

import com.enigma.chateringapp.model.entity.Menu;
import com.enigma.chateringapp.model.entity.MenuPrice;
import com.enigma.chateringapp.model.request.CustomerRequest;
import com.enigma.chateringapp.model.response.CustomerResponse;

public interface MenuPriceService {
    MenuPrice create(MenuPrice menuPrice);
    MenuPrice getById(String id);
    MenuPrice findMenuPriceIsActive(String productId, Boolean active);
    MenuPrice update(MenuPrice menuPrice);
    void delete(String id);
    MenuPrice save(MenuPrice menuPrice);
}
