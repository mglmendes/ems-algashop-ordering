package com.algaworks.algashop.ordering.core.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.core.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.generic.ErrorMessages;

public class OrderNotFoundException extends DomainEntityNotFoundException {
    public OrderNotFoundException(String orderId) {
       super(String.format(
               ErrorMessages.ERROR_ORDER_NOT_FOUND,
               orderId
       ));
    }
}
