package com.algaworks.algashop.ordering.domain.model.order.event;

import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

import java.time.OffsetDateTime;

public record OrderPaidEvent(OrderId orderId, CustomerId customerId, OffsetDateTime paidAt) {
}
