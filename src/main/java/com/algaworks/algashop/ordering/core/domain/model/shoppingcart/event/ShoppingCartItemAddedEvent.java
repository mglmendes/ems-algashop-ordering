package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.event;

import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject.ShoppingCartId;

import java.time.OffsetDateTime;

public record ShoppingCartItemAddedEvent(ShoppingCartId shoppingCartId,
                                         CustomerId customerId,
                                         ProductId productId,
                                         OffsetDateTime addedAt) {
}
