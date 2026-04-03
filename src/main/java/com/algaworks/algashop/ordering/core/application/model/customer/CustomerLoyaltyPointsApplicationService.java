package com.algaworks.algashop.ordering.core.application.model.customer;

import com.algaworks.algashop.ordering.core.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.core.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.core.domain.model.customer.service.CustomerLoyaltyPointsService;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.core.domain.model.order.exceptions.OrderNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.OrderId;
import com.algaworks.algashop.ordering.core.ports.in.customer.ForAddingLoyaltyPoints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerLoyaltyPointsApplicationService implements ForAddingLoyaltyPoints {

    private final Customers customers;

    private final Orders orders;

    private final CustomerLoyaltyPointsService customerLoyaltyPointsService;

    @Transactional
    @Override
    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId) {
        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(rawOrderId);
        CustomerId customerId = new CustomerId(rawCustomerId);
        OrderId orderId = new OrderId(rawOrderId);

        Customer customer = customers.ofId(customerId).orElseThrow(
                () -> new CustomerNotFoundException()
        );

        Order order = orders.ofId(orderId).orElseThrow(
                () -> new OrderNotFoundException(rawOrderId)
        );

        customerLoyaltyPointsService.addPoints(customer, order);
        customers.add(customer);
    }
}
