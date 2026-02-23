package com.algaworks.algashop.ordering.domain.model.generic;

public class DomainEntityNotFoundException extends RuntimeException {
    public DomainEntityNotFoundException(String message) {
        super(message);
    }
}
