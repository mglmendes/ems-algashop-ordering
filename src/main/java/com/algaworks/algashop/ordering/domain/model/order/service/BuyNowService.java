package com.algaworks.algashop.ordering.domain.model.order.service;

import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Billing;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Shipping;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;

@DomainService
public class BuyNowService {

    public Order buyNow(Product product, CustomerId customerId, Billing billingInfo, Shipping shippingInfo,
                        Quantity quantity, PaymentMethod paymentMethod) {
        product.checkOutOfStock();

        Order order = Order.draft(customerId);
        order.changeBilling(billingInfo);
        order.changeShipping(shippingInfo);
        order.changePaymentMethod(paymentMethod);
        order.addItem(product, quantity);
        order.place();

        return order;
    }
}
