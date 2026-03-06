package com.algaworks.algashop.ordering.domain.model.customer.exception;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;

public class CustomerEmailAlreadyInUseException extends DomainException {

    public CustomerEmailAlreadyInUseException() {
        super(ErrorMessages.ERROR_EMAIL_ALREADY_IN_USE);

    }
}
