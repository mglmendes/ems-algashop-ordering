package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject;

import com.algaworks.algashop.ordering.core.domain.model.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record ShoppingCartId(UUID value) {

    public ShoppingCartId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public ShoppingCartId {
        Objects.requireNonNull(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
