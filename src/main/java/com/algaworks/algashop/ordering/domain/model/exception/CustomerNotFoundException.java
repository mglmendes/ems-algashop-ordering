package com.algaworks.algashop.ordering.domain.model.exception;

import java.util.UUID;

public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(UUID customerId) {
       super(String.format(
               ErrorMessages.ERROR_CUSTOMER_NOT_FOUND,
               customerId
       ));
    }
}
