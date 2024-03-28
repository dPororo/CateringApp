package com.enigma.chateringapp.controller;

import com.enigma.chateringapp.model.entity.Menu;
import com.enigma.chateringapp.model.request.MenuRequest;
import com.enigma.chateringapp.model.response.CommonResponse;
import com.enigma.chateringapp.model.response.MenuResponse;
import com.enigma.chateringapp.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody MenuRequest menuRequest) {
        MenuResponse menuResponse = menuService.create(menuRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<MenuResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new menu and price!")
                        .data(menuResponse)
                        .build());
    }

    @GetMapping
    public List<Menu> getAllMenu() {
        return menuService.getAll();
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllMenuPage(@RequestParam(required = false) String name, @RequestParam(required = false) Long maxPrice, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "3") Integer size) {
        Page<MenuResponse> menuPage = menuService.getAllByNameOrPrice(name, maxPrice, page, size);
        return ResponseEntity.ok(CommonResponse.<Page<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully get all menu")
                .data(menuPage)
                .build());
    }

    @PutMapping
    private ResponseEntity<?> updateMenu(@RequestBody MenuRequest menuRequest) {
        MenuResponse menuResponse = menuService.update(menuRequest);
        return ResponseEntity.ok(CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully updated menu")
                .data(menuResponse)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable String id) {
        try {
            MenuResponse menuResponse = menuService.getById(id);
            return ResponseEntity.ok(CommonResponse.<MenuResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Successfully get menu by id")
                    .data(menuResponse)
                    .build());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.ofNullable(CommonResponse.<MenuResponse>builder()
                    .statusCode(HttpStatus.NO_CONTENT.value())
                    .message("Menu with id " + id + " is not found")
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        menuService.delete(id);
        return ResponseEntity.ok(CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully deleted menu")
                .build());
    }
}
