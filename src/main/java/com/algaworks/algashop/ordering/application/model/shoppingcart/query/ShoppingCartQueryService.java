package com.algaworks.algashop.ordering.application.model.shoppingcart.query;

import com.algaworks.algashop.ordering.application.model.shoppingcart.output.ShoppingCartOutput;

import java.util.UUID;

public interface ShoppingCartQueryService {
    ShoppingCartOutput findById(UUID shoppingCartId);
    ShoppingCartOutput findByCustomerId(UUID customerId);
}
