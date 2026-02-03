package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(Order aggragateOrder) {
        return merge(new OrderPersistenceEntity(), aggragateOrder);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity orderEntity, Order aggregateOrder) {
        orderEntity.setId(aggregateOrder.id().value().toLong());
        orderEntity.setCustomerId(aggregateOrder.customerId().value());
        orderEntity.setTotalAmount(aggregateOrder.totalAmount().value());
        orderEntity.setTotalItems(aggregateOrder.totalItems().value());
        orderEntity.setStatus(aggregateOrder.status().name());
        orderEntity.setPaymentMethod(aggregateOrder.paymentMethod().name());
        orderEntity.setPlacedAt(aggregateOrder.placedAt());
        orderEntity.setPaidAt(aggregateOrder.paidAt());
        orderEntity.setCanceledAt(aggregateOrder.canceledAt());
        orderEntity.setReadyAt(aggregateOrder.readyAt());
        return orderEntity;
    }
}
