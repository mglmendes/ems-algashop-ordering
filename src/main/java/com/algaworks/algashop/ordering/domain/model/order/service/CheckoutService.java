package com.algaworks.algashop.ordering.domain.model.order.service;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.exception.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Billing;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.Shipping;

import java.util.Set;

@DomainService
public class CheckoutService {

    public Order checkout(ShoppingCart shoppingCart, Billing billingInfo,
                          Shipping shippingInfo, PaymentMethod paymentMethod) {

        if (shoppingCart.containsUnavailableItems() || shoppingCart.isEmpty()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        Set<ShoppingCartItem> items = shoppingCart.items();

        Order order = Order.draft(shoppingCart.customerId());

        order.changeBilling(billingInfo);
        order.changeShipping(shippingInfo);
        order.changePaymentMethod(paymentMethod);

        items.forEach(
                item -> {
                    order.addItem(new Product(
                            item.productId(),
                            item.name(),
                            item.price(),
                            item.isAvailable()), item.quantity());
                });


        order.place();
        shoppingCart.empty();

        return order;
    }
}
