package com.algaworks.algashop.ordering.domain.model.order.service;

import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Billing;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Shipping;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import lombok.RequiredArgsConstructor;

import java.time.Year;

@DomainService
@RequiredArgsConstructor
public class BuyNowService {

    private final Orders orders;

    public Order buyNow(Product product,
                        Customer customer, Billing billingInfo, Shipping shippingInfo,
                        Quantity quantity, PaymentMethod paymentMethod) {
        product.checkOutOfStock();

        Order order = Order.draft(customer.id());
        order.changeBilling(billingInfo);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);

        if (haveFreeShipping(customer)) {
            Shipping freeShipping = shippingInfo.toBuilder().cost(Money.ZERO).build();
            order.changeShipping(freeShipping);
        } else {
            order.changeShipping(shippingInfo);
        }

        order.place();

        return order;
    }

    private boolean haveFreeShipping(Customer customer) {
        return customer.loyaltyPoints().compareTo(new LoyaltyPoints(100)) >= 0
                && orders.salesQuantityByCustomerInYear(customer.id(), Year.now()) >= 2
                || customer.loyaltyPoints().compareTo(new LoyaltyPoints(2000)) >= 0;
    }
}
