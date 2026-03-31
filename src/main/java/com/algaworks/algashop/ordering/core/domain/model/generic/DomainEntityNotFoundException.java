package com.algaworks.algashop.ordering.core.domain.model.generic;

public class DomainEntityNotFoundException extends RuntimeException {
    public DomainEntityNotFoundException(String message) {
        super(message);
    }
}
