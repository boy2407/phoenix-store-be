package com.example.phoenixstorebe.service.impl;

import com.example.phoenixstorebe.entity.Cart;
import com.example.phoenixstorebe.entity.CartItem;
import com.example.phoenixstorebe.entity.ProductVariant;
import com.example.phoenixstorebe.entity.User;
import com.example.phoenixstorebe.exception.BadRequestException;
import com.example.phoenixstorebe.payload.cart.CartItemCreateRequest;
import com.example.phoenixstorebe.repository.CartItemRepository;
import com.example.phoenixstorebe.repository.CartRepository;
import com.example.phoenixstorebe.repository.ProductVariantRepository;
import com.example.phoenixstorebe.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final String SESSION_CART = "SESSION_CART";
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public Cart getCart(User user, HttpSession session) {
        if (user != null) {
            return cartRepository.findByUser(user).orElseGet(() -> {
                Cart cart = new Cart();
                cart.setUser(user);
                return cartRepository.save(cart);
            });
        } else {
            Cart cart = (Cart) session.getAttribute(SESSION_CART);
            if (cart == null) {
                cart = new Cart();
                session.setAttribute(SESSION_CART, cart);
            }
            return cart;
        }
    }

    @Override
    @Transactional
    public void removeItem(Long cartItemId, User user, HttpSession session) {
        Cart cart = getCart(user, session);
        if (user == null) {
            // Xóa khỏi session cart theo id
            cart.removeItem(cartItemId);
            session.setAttribute(SESSION_CART, cart);
        } else {
            cartItemRepository.deleteById(cartItemId);
            cart.removeItem(cartItemId);
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void clearCart(User user, HttpSession session) {
        Cart cart = getCart(user, session);
        cart.clearItems();
        if (user == null) {
            session.setAttribute(SESSION_CART, cart);
        } else {
            cartItemRepository.deleteAll(cart.getItems());
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void addOrUpdateItem(CartItemCreateRequest request, User user, HttpSession session) {
        Cart cart = getCart(user, session);
        if (request.getQuantity() == 0) {
            removeItemByVariantId(cart, request.getVariantId());
            persistCart(cart, user, session);
            return;
        }
        Long variantId = request.getVariantId();
        Integer quantity = request.getQuantity();

        ProductVariant variant = productVariantRepository.findById(variantId).orElseThrow(() -> new BadRequestException("Variant not found"));
        addOrUpdateCartItem(cart,variantId, request.getQuantity(), false, Map.of(variantId, variant));
        persistCart(cart, user, session);
    }

    @Override
    @Transactional
    public void mergeCartSessionToDb(User user, HttpSession session) {
        if (user == null) return;

        Cart dbCart = getOrCreateUserCart(user);
        Cart sessionCart = (Cart) session.getAttribute(SESSION_CART);

        // Login sync policy:
        // - session empty/null: keep DB as source of truth
        // - session has items: merge into DB (sum quantity when same variant)
        if (sessionCart != null && !sessionCart.getItems().isEmpty()) {
            Map<Long, ProductVariant> variantById = loadVariantById(sessionCart.getItems());
            for (CartItem sessionItem : sessionCart.getItems()) {
                addOrUpdateCartItem(
                        dbCart,
                        sessionItem.getVariantId(),
                        sessionItem.getQuantity(),
                        true,
                        variantById
                );
            }
        }
        session.setAttribute(SESSION_CART, dbCart);
        cartRepository.save(dbCart);
    }


    //Cart Database
    private Cart getOrCreateUserCart(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }


    private void persistCart(Cart cart, User user, HttpSession session) {
        if (user == null) {
            session.setAttribute(SESSION_CART, cart);
            return;
        }
        cartRepository.save(cart);
    }

    private void removeItemByVariantId(Cart cart, Long variantId) {
        cart.removeItemByVariantId(variantId);
    }


    private void addOrUpdateCartItem(Cart cart, Long variantId, int quantity, boolean addQuantityIfExists, Map<Long,ProductVariant> variantById) {
        if (variantId == null || quantity <= 0) {
            return;
        }

        ProductVariant variant = variantById.get(variantId);
        if (variant == null) {
            throw new BadRequestException("Variant not found");
        }

        String name =  variant.getProduct().getName();
        BigDecimal price = variant.getPrice();

        CartItem found = cart.findItemByVariantId(variantId).orElse(null);

        if (found != null) {
            int updatedQuantity = addQuantityIfExists ? found.getQuantity() + quantity : quantity;
            found.setQuantity(updatedQuantity);
            found.setPrice(price);
            found.setTotalPrice(price.multiply(BigDecimal.valueOf(updatedQuantity)));
            found.setName(name);
        } else {
            CartItem item = new CartItem();
            item.setVariantId(variantId);
            item.setName(name);
            item.setQuantity(quantity);
            item.setPrice(price);
            item.setTotalPrice(price.multiply(BigDecimal.valueOf(quantity)));
            item.setCart(cart);
            cart.addItem(item);
        }
    }

    private Map<Long, ProductVariant> loadVariantById(List<CartItem> items) {
        Set<Long> variantIds = items.stream()
                .map(CartItem::getVariantId)
                .filter(id -> id != null && id > 0)
                .collect(Collectors.toSet());

        return productVariantRepository.findAllById(variantIds).stream()
                .collect(Collectors.toMap(ProductVariant::getId, variant -> variant));
    }
}
