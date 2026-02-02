package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

public class OrderStatusCannotBeChangedException extends DomainException{

    public OrderStatusCannotBeChangedException(OrderId orderId, OrderStatus currentStatus, OrderStatus newStatus) {
        super(String.format(
                ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED, orderId, currentStatus, newStatus
        ));
    }
}
