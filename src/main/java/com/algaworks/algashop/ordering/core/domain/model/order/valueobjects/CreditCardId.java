package com.algaworks.algashop.ordering.core.domain.model.order.valueobjects;

import com.algaworks.algashop.ordering.core.domain.model.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

public record CreditCardId(UUID id) {
    public CreditCardId() {
        this(IdGenerator.generateTimeBasedUUID());
    }
    public CreditCardId {
        Objects.requireNonNull(id);
    }

}
