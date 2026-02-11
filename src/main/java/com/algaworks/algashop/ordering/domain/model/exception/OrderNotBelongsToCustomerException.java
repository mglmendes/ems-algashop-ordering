package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderNotBelongsToCustomerException extends DomainException {

    public OrderNotBelongsToCustomerException(CustomerId customerId, OrderId orderId) {
        super(String.format(ErrorMessages.ERROR_ORDER_NOT_BELONGS_TO_CUSTOMER, orderId, customerId));
    }
}
