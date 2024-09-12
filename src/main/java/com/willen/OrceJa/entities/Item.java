package com.willen.OrceJa.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    private UUID itemId;

    private String title;

    private int quantity;

    private BigDecimal price;

    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonBackReference
    private Budget budget;

    public Item() {
    }

    public Item(UUID itemId, String title, int quantity, BigDecimal price, BigDecimal subtotal) {
        this.itemId = itemId;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
