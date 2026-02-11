package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

public class CannotAddLoyaltyPointsToANonReadyOrder extends DomainException {
    public CannotAddLoyaltyPointsToANonReadyOrder(OrderId orderId) {
        super(String.format(
                ErrorMessages.ERROR_CANNOT_ADD_LOYALTY_POINTS_TO_A_NON_READY_ORDER,
                orderId
        ));
    }
}
