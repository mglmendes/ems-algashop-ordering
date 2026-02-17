package com.algaworks.algashop.ordering.domain.model.order.repository;

import com.algaworks.algashop.ordering.domain.model.generic.Repository;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.common.Money;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;

import java.time.Year;
import java.util.List;

public interface Orders extends Repository<Order, OrderId> {
    List<Order> placedByCustomerInYear(CustomerId customerId, Year year);

    long salesQuantityByCustomerInYear(CustomerId customerId, Year year);

    Money totalSoldForCustomer(CustomerId customerId);
}
