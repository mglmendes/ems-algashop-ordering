package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.service;

import com.algaworks.algashop.ordering.core.domain.model.common.Money;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;

public interface ShoppingCartProductAdjustmentService {

    void adjustPrice(ProductId productId, Money updatedPrice);

    void changeAvailability(ProductId productId, boolean available);
}
