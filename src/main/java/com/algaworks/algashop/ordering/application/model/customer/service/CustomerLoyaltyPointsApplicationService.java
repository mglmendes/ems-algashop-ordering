package com.algaworks.algashop.ordering.application.model.customer.service;

import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CannotAddLoyaltyPointsToANonReadyOrder;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.service.CustomerLoyaltyPointsService;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.exceptions.OrderNotBelongsToCustomerException;
import com.algaworks.algashop.ordering.domain.model.order.exceptions.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerLoyaltyPointsApplicationService {

    private final Customers customers;

    private final Orders orders;

    private final CustomerLoyaltyPointsService customerLoyaltyPointsService;

    @Transactional
    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId) {
        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(rawOrderId);
        CustomerId customerId = new CustomerId(rawCustomerId);
        OrderId orderId = new OrderId(rawOrderId);

        Customer customer = customers.ofId(customerId).orElseThrow(
                () -> new CustomerNotFoundException(rawCustomerId)
        );

        Order order = orders.ofId(orderId).orElseThrow(
                () -> new OrderNotFoundException(rawOrderId)
        );

        customerLoyaltyPointsService.addPoints(customer, order);
        customers.add(customer);
    }
}
