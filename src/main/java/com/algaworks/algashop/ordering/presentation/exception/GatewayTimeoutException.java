package com.algaworks.algashop.ordering.presentation.exception;

public class GatewayTimeoutException extends RuntimeException {

    public GatewayTimeoutException() {
    }

    public GatewayTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
