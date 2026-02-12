package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

import static com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST;

public class OrderInvalidShippingDeliveryDateException extends DomainException {


    public OrderInvalidShippingDeliveryDateException(OrderId orderId) {
        super(
                String.format(ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST, orderId)
        );
    }
}
