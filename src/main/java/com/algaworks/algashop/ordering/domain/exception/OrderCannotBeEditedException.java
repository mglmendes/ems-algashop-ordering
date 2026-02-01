package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

public class OrderCannotBeEditedException extends DomainException{

    public OrderCannotBeEditedException(OrderId orderId, OrderStatus status) {
        super(String.format(
                ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED,
                orderId,
                status
        ));
    }
}
