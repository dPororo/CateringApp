package com.enigma.chateringapp.repository;

import com.enigma.chateringapp.model.entity.Menu;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {
    @Modifying
    @Query(value = "INSERT INTO m_menu (menu_id, menu_name, menu_price, menu_description) VALUES (:#{#menu.id}, :#{#menu.name}, :#{#menu.menuPrices}, :#{#menu.description})", nativeQuery = true)
    void insertMenu(Menu menu);

    @Transactional
    default void insertAndFlush(Menu menu) {
        menu.setId(UUID.randomUUID().toString());
        insertMenu(menu);
        flush();
    }
}
