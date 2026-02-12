package com.algaworks.algashop.ordering.domain.model.order.exceptions;

import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import com.algaworks.algashop.ordering.domain.model.generic.ErrorMessages;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

public class CannotAddLoyaltyPointsToANonReadyOrder extends DomainException {
    public CannotAddLoyaltyPointsToANonReadyOrder(OrderId orderId) {
        super(String.format(
                ErrorMessages.ERROR_CANNOT_ADD_LOYALTY_POINTS_TO_A_NON_READY_ORDER,
                orderId
        ));
    }
}
