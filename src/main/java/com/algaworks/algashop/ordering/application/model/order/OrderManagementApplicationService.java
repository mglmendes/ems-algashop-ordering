package com.algaworks.algashop.ordering.application.model.order;

import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.exceptions.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderManagementApplicationService {

    private final Orders orders;

    @Transactional
    public void cancel(String orderId) {
        Order order = findOrder(orderId);

        order.cancel();
        orders.add(order);
    }

    @Transactional
    public void markAsPaid(String orderId) {
        Order order = findOrder(orderId);
        order.markAsPaid();
        orders.add(order);
    }

    @Transactional
    public void markAsReady(String orderId) {
        Order order = findOrder(orderId);
        order.markAsReady();
        orders.add(order);
    }

    @Nonnull
    private Order findOrder(String orderId) {
        return orders.ofId(new OrderId(orderId)).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
    }
}
