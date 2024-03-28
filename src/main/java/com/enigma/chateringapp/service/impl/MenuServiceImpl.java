package com.enigma.chateringapp.service.impl;

import com.enigma.chateringapp.model.entity.Menu;
import com.enigma.chateringapp.model.entity.MenuPrice;
import com.enigma.chateringapp.model.request.MenuRequest;
import com.enigma.chateringapp.model.response.MenuResponse;
import com.enigma.chateringapp.repository.MenuRepository;
import com.enigma.chateringapp.service.MenuPriceService;
import com.enigma.chateringapp.service.MenuService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuPriceService menuPriceService;
    private final MenuRepository menuRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public MenuResponse create(MenuRequest menuRequest) {
        Menu menu = Menu.builder()
                .name(menuRequest.getName())
                .description(menuRequest.getDescription())
                .build();
        menuRepository.insertAndFlush(menu);

        MenuPrice menuPrice = MenuPrice.builder()
                .menu(menu)
                .price(menuRequest.getPrice())
                .isActive(true)
                .stock(menuRequest.getStock())
                .build();
        menuPriceService.create(menuPrice);

        return MenuResponse.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menuPrice.getPrice())
                .stock(menuPrice.getStock())
                .build();
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    @Override
    public Page<MenuResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        Specification<Menu> specification = (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("menuPrices").get("price"), maxPrice));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        }));

        Pageable pageable = PageRequest.of(page, size);
        Page<Menu> menus = menuRepository.findAll(specification, pageable);

        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus.getContent()) {
            Optional<MenuPrice> menuPrice = menu.getMenuPrices()
                    .stream()
                    .filter(MenuPrice::getIsActive).findFirst();
            if (menuPrice.isEmpty()) continue;
            menuResponses.add(MenuResponse.builder()
                    .menuId(menu.getId())
                    .name(menu.getName())
                    .description(menu.getDescription())
                    .price(menuPrice.get().getPrice())
                    .stock(menuPrice.get().getStock())
                    .build());
        }
        return new PageImpl<>(menuResponses, pageable, menus.getTotalElements());
    }

    @Override
    public MenuResponse update(MenuRequest menuRequest) {
        Menu currentMenu = menuRepository.findById(menuRequest.getMenuId()).orElse(null);
        if (currentMenu != null) {
            currentMenu.setName(menuRequest.getName());
            currentMenu.setDescription(menuRequest.getDescription());

            MenuPrice activeMenuPrice = currentMenu.getMenuPrices().stream()
                    .filter(MenuPrice::getIsActive)
                    .findFirst()
                    .orElse(null);

            if (activeMenuPrice != null) {
                activeMenuPrice.setPrice(menuRequest.getPrice());
                activeMenuPrice.setStock(menuRequest.getStock());
                menuPriceService.update(activeMenuPrice);
            } else {
                MenuPrice newMenuPrice = new MenuPrice();
                newMenuPrice.setPrice(menuRequest.getPrice());
                newMenuPrice.setStock(menuRequest.getStock());
                newMenuPrice.setMenu(currentMenu);
                newMenuPrice.setIsActive(true);

                currentMenu.getMenuPrices().add(newMenuPrice);
                menuPriceService.create(newMenuPrice);
            }
            menuRepository.save(currentMenu);

            return MenuResponse.builder()
                    .menuId(currentMenu.getId())
                    .name(currentMenu.getName())
                    .description(currentMenu.getDescription())
                    .price(currentMenu.getMenuPrices().stream().filter(MenuPrice::getIsActive).findFirst().get().getPrice())
                    .stock(currentMenu.getMenuPrices().stream().filter(MenuPrice::getIsActive).findFirst().get().getStock())
                    .build();
        }
        return null;
    }

    @Override
    public MenuResponse getById(String id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu != null) {
            return MenuResponse.builder()
                    .menuId(menu.getId())
                    .name(menu.getName())
                    .description(menu.getDescription())
                    .price(menu.getMenuPrices().stream().filter(MenuPrice::getIsActive).findFirst().get().getPrice())
                    .stock(menu.getMenuPrices().stream().filter(MenuPrice::getIsActive).findFirst().get().getStock())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Optional<Menu> menuOptional = menuRepository.findById(id);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();

            for (MenuPrice menuPrice : menu.getMenuPrices()) {
                menuPrice.setIsActive(false);
                menuPriceService.save(menuPrice);
            }
        } else {
            throw new RuntimeException("Menu dengan ID " + id + " tidak ditemukan");
        }
    }
}
