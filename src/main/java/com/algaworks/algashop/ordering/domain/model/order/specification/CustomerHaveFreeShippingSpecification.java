package com.algaworks.algashop.ordering.domain.model.order.specification;

import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.specification.Specification;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@RequiredArgsConstructor
public class CustomerHaveFreeShippingSpecification implements Specification<Customer> {

    private final int minPointsForFreeShippingRule1;
    private final long salesQuantityByYearForFreeShippingRule1;

    private final int minPointsForFreeShippingRule2;

    private final Orders orders;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return customer.loyaltyPoints().compareTo(new LoyaltyPoints(minPointsForFreeShippingRule1)) >= 0
                && orders.salesQuantityByCustomerInYear(customer.id(), Year.now()) >= salesQuantityByYearForFreeShippingRule1
                || customer.loyaltyPoints().compareTo(new LoyaltyPoints(minPointsForFreeShippingRule2)) >= 0;
    }
}
