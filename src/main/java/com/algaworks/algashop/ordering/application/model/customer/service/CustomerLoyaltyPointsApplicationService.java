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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerLoyaltyPointsApplicationService {

    private final Customers customers;

    private final Orders orders;

    private final CustomerLoyaltyPointsService customerLoyaltyPointsService;

    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId) {
        CustomerId customerId = new CustomerId(rawCustomerId);
        OrderId orderId = new OrderId(rawOrderId);
        if (!customers.exists(customerId)) {
            throw new CustomerNotFoundException(rawCustomerId);
        }

        if (!orders.exists(new OrderId(rawOrderId))) {
            throw new OrderNotFoundException(rawOrderId);
        }

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
