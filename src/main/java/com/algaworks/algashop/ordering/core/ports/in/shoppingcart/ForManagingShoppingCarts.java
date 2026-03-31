package com.algaworks.algashop.ordering.core.ports.in.shoppingcart;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.input.ShoppingCartItemInput;

import java.util.UUID;

public interface ForManagingShoppingCarts {
    UUID createNew(UUID rawCustomerId);
    void addItem(ShoppingCartItemInput input);
    void removeItem(UUID shoppingCartId, UUID shoppingCartItemId);
    void empty(UUID shoppingCartId);
    void delete(UUID shoppingCartId);
}
