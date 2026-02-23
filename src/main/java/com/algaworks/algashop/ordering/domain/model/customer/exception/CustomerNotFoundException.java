package com.algaworks.algashop.ordering.domain.model.customer.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;

import java.util.UUID;

public class CustomerNotFoundException extends DomainEntityNotFoundException {
    public CustomerNotFoundException(UUID customerId) {
       super(String.format(
               ErrorMessages.ERROR_CUSTOMER_NOT_FOUND,
               customerId
       ));
    }
}
