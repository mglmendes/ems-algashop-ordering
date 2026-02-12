package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

public class OrderCannotBeEditedException extends DomainException {

    public OrderCannotBeEditedException(OrderId orderId, OrderStatus status) {
        super(String.format(
                ErrorMessages.ERROR_ORDER_CANNOT_BE_EDITED,
                orderId,
                status
        ));
    }
}
