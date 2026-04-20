package com.example.phoenixstorebe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Setter
@Getter
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();

    public BigDecimal totalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void addItem(CartItem item) {
        for (CartItem ci : items) {
            if (item.getVariantId() != null && item.getVariantId().equals(ci.getVariantId())) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                ci.setTotalPrice(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
                return;
            }
            if (item.getVariantId() == null && ci.getVariantId() == null && item.getId() != null && item.getId().equals(ci.getId())) {
                ci.setQuantity(ci.getQuantity() + item.getQuantity());
                ci.setTotalPrice(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
                return;
            }
        }
        item.setCart(this);
        item.setTotalPrice(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        items.add(item);
    }
    public void clearItems() {
        items.clear();
    }
    public void updateItem(Long cartItemId, int quantity) {
        for (CartItem ci : items) {
            if (ci.getId() != null && ci.getId().equals(cartItemId)) {
                ci.setQuantity(quantity);
                ci.setTotalPrice(ci.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
                return;
            }
        }
    }
    public void removeItem(Long cartItemId) {
        items.removeIf(ci -> ci.getId() != null && ci.getId().equals(cartItemId));
    }
    public Optional<CartItem> findItemByVariantId(Long variantId) {
        if (variantId == null) {
            return Optional.empty();
        }
        return items.stream()
                .filter(ci -> variantId.equals(ci.getVariantId()))
                .findFirst();
    }

    public void removeItemByVariantId(Long variantId) {
        if (variantId == null) {
            return;
        }
        items.removeIf(ci -> variantId.equals(ci.getVariantId()));
    }
}
