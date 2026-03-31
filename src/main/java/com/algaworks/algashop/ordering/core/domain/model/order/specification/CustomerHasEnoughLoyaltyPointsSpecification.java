package com.algaworks.algashop.ordering.core.domain.model.order.specification;

import com.algaworks.algashop.ordering.core.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.LoyaltyPoints;
import com.algaworks.algashop.ordering.core.domain.model.specification.Specification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerHasEnoughLoyaltyPointsSpecification implements Specification<Customer> {

    private final LoyaltyPoints expectedLoyaltyPoints;

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return customer.loyaltyPoints().compareTo(expectedLoyaltyPoints) >= 0;
    }
}
