package com.algaworks.algashop.ordering.domain.model.exception;

public class ShoppingCartCantProceedToCheckoutException extends DomainException{

    public ShoppingCartCantProceedToCheckoutException() {
        super(ErrorMessages.ERROR_SHOPPING_CART_CANT_PROCEED_TO_CHECKOUT);
    }
}
