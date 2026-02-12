package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderItemId;

import static com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages.ERROR_ORDER_DOES_NOT_CONTAIN_ODER_ITEM;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(OrderId orderId, OrderItemId orderItemId) {
        super(String.format(
                ERROR_ORDER_DOES_NOT_CONTAIN_ODER_ITEM,
                orderId,
                orderItemId
        ));
    }
}
