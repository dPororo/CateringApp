package com.enigma.chateringapp.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m_menu_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MenuPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "price", nullable = false, columnDefinition = "bigInt check (price >= 0)")
    private Long price;

    @Column(name = "stock", nullable = false, columnDefinition = "int check (stock >= 0)")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    @JsonBackReference
    private Menu menu;

    @Column(name = "is_active")
    private Boolean isActive;
}
