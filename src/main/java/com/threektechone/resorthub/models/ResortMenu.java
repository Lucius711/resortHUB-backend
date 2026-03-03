package com.threektechone.resorthub.models;

import java.math.BigDecimal;

import com.threektechone.resorthub.enums.MenuCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="resort_menu")
public class ResortMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuId;

    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private MenuCategory category;

    public ResortMenu() {};

    public ResortMenu(String name,BigDecimal price,MenuCategory category) {
        this.name = name;
        this.price= price;
        this.category = category;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public Resort getResort() {
        return resort;
    }

    public void setResort(Resort resort) {
        this.resort = resort;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuCategory getMenuCategory() {
        return category;
    }

    public void setMenuCategory(MenuCategory category) {
        this.category = category;
    }



    
}
