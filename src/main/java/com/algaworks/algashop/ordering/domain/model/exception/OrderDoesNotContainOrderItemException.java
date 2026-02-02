package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderItemId;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ODER_ITEM;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(OrderId orderId, OrderItemId orderItemId) {
        super(String.format(
                ERROR_ORDER_DOES_NOT_CONTAIN_ODER_ITEM,
                orderId,
                orderItemId
        ));
    }
}
