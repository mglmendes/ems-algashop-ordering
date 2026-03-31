package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.exception;

import com.algaworks.algashop.ordering.core.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject.ShoppingCartItemId;

public class ShoppingCartItemIncompatibleProductException extends DomainException {
    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}