package com.algaworks.algashop.ordering.core.ports.in.shoppingcart;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.output.ShoppingCartOutput;

import java.util.UUID;

public interface ForQueryingShoppingCarts {
    ShoppingCartOutput findById(UUID shoppingCartId);
    ShoppingCartOutput findByCustomerId(UUID customerId);
}
