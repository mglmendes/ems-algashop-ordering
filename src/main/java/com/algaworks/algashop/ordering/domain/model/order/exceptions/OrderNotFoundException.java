package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import io.hypersistence.tsid.TSID;

import java.util.UUID;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String orderId) {
       super(String.format(
               ErrorMessages.ERROR_ORDER_NOT_FOUND,
               orderId
       ));
    }
}
