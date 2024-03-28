package com.enigma.chateringapp.repository;

import com.enigma.chateringapp.model.entity.MenuPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuPriceRepository extends JpaRepository<MenuPrice, String> {
    Optional<MenuPrice> findByMenu_IdAndIsActive(String menuId, Boolean active);

}
