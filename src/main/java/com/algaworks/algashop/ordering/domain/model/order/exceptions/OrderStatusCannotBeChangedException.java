package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

public class OrderStatusCannotBeChangedException extends DomainException {

    public OrderStatusCannotBeChangedException(OrderId orderId, OrderStatus currentStatus, OrderStatus newStatus) {
        super(String.format(
                ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, orderId, currentStatus, newStatus
        ));
    }
}
