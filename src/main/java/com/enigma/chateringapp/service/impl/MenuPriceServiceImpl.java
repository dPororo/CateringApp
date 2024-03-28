package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.model.entity.MenuPrice;
import com.enigma.chateringapp.repository.MenuPriceRepository;
import com.enigma.chateringapp.service.MenuPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuPriceServiceImpl implements MenuPriceService {
    private final MenuPriceRepository menuPriceRepository;
    @Override
    public MenuPrice create(MenuPrice menuPrice) {
        return menuPriceRepository.save(menuPrice);
    }

    @Override
    public MenuPrice getById(String id) {
        return menuPriceRepository.findById(id).orElse(null);
    }

    @Override
    public MenuPrice findMenuPriceIsActive(String productId, Boolean active) {
        return menuPriceRepository.findByMenu_IdAndIsActive(productId, active).orElse(null);
    }

    @Override
    public MenuPrice update(MenuPrice menuPrice) {
        return menuPriceRepository.save(menuPrice);
    }

    @Override
    public void delete(String id) {
        menuPriceRepository.deleteById(id);
    }

    @Override
    public MenuPrice save(MenuPrice menuPrice) {
        menuPriceRepository.save(menuPrice);
        return menuPrice;
    }
}
