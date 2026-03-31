package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.event;

import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject.ShoppingCartId;

import java.time.OffsetDateTime;

public record ShoppingCartEmptiedEvent(ShoppingCartId shoppingCartId,
                                       CustomerId customerId,
                                       OffsetDateTime emptiedAt) {
}
