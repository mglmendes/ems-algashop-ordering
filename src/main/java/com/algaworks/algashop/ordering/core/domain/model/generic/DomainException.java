package com.algaworks.algashop.ordering.core.domain.model.generic;

public class DomainException extends RuntimeException{

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}
