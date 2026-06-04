package com.algaworks.algashop.ordering.infrastructure.config.exceptionhandler;

public class BadGatewayException extends RuntimeException {

    public BadGatewayException() {
    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class InternalServerErrorException extends BadGatewayException {

        public InternalServerErrorException() {
        }

        public InternalServerErrorException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ClientErrorException extends BadGatewayException {

        public ClientErrorException() {
        }

        public ClientErrorException(String message, Throwable cause) {
            super(message, cause);
        }
    }


}
