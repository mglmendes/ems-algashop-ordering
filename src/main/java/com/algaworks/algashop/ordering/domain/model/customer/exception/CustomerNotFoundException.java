package com.algaworks.algashop.ordering.domain.model.customer.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;

public class CustomerNotFoundException extends DomainEntityNotFoundException {
    public CustomerNotFoundException() {
       super(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND);
    }
}
