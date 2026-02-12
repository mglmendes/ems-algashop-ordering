package com.algaworks.algashop.ordering.domain.model.shoppingcart.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;

public class ShoppingCartCantProceedToCheckoutException extends DomainException {

    public ShoppingCartCantProceedToCheckoutException() {
        super(ErrorMessages.ERROR_SHOPPING_CART_CANT_PROCEED_TO_CHECKOUT);
    }
}
