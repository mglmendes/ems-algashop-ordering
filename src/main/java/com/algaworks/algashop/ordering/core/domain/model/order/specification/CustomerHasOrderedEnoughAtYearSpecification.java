package com.algaworks.algashop.ordering.core.domain.model.order.specification;

import com.algaworks.algashop.ordering.core.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.core.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.core.domain.model.specification.Specification;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@RequiredArgsConstructor
public class CustomerHasOrderedEnoughAtYearSpecification implements Specification<Customer> {

    private final long expectedOrdersAtYear;
    private final Orders orders;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return orders.salesQuantityByCustomerInYear(customer.id(), Year.now()) >= expectedOrdersAtYear;
    }
}
