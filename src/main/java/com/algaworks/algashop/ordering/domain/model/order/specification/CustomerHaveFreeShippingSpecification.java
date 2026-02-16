package com.algaworks.algashop.ordering.domain.model.order.specification;

import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.specification.Specification;

public class CustomerHaveFreeShippingSpecification implements Specification<Customer> {

    private final CustomerHasOrderedEnoughAtYearSpecification hasOrderedEnoughAtYear;
    private final CustomerHasEnoughLoyaltyPointsSpecification hasEnoughMinLoyaltyPoints;
    private final CustomerHasEnoughLoyaltyPointsSpecification hasEnoughPremiumLoyaltyPoints;

    public CustomerHaveFreeShippingSpecification(
            Orders orders,
            LoyaltyPoints minPointsForFreeShipping,
            long salesQuantityForFreeShipping,
            LoyaltyPoints premiumPointsForFreeShipping
    ) {
        this.hasOrderedEnoughAtYear = new CustomerHasOrderedEnoughAtYearSpecification(
                salesQuantityForFreeShipping,
                orders);

        this.hasEnoughMinLoyaltyPoints = new CustomerHasEnoughLoyaltyPointsSpecification(minPointsForFreeShipping);

        this.hasEnoughPremiumLoyaltyPoints = new CustomerHasEnoughLoyaltyPointsSpecification(premiumPointsForFreeShipping);
    }

    @Override
    public boolean isSatisfiedBy(Customer customer) {
        return hasEnoughMinLoyaltyPoints
                .and(hasOrderedEnoughAtYear)
                .or(hasEnoughPremiumLoyaltyPoints)
                .isSatisfiedBy(customer);
    }
}
