package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

public class OrderNotBelongsToCustomerException extends DomainException {

    public OrderNotBelongsToCustomerException(OrderId orderId) {
        super(String.format(ErrorMessages.ERROR_ORDER_NOT_BELONGS_TO_CUSTOMER, orderId));
    }
}
