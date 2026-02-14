package com.algaworks.algashop.ordering.domain.model.customer.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;

import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages.ERROR_CUSTOMER_ARCHIVED;

public class CustomerArchivedException extends DomainException {

    public CustomerArchivedException(Throwable cause) {
        super(ERROR_CUSTOMER_ARCHIVED, cause);
    }

    public CustomerArchivedException(UUID customerId) {
        super(String.format(
                ERROR_CUSTOMER_ARCHIVED,
                customerId
        ));
    }
}
