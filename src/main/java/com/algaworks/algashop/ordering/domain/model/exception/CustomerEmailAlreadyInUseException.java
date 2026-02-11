package com.algaworks.algashop.ordering.domain.model.exception;

import java.util.UUID;

public class CustomerEmailAlreadyInUseException extends DomainException{

    public CustomerEmailAlreadyInUseException(String email) {
        super(String.format(
                ErrorMessages.ERROR_EMAIL_ALREADY_IN_USE,
                email
        ));

    }
}
