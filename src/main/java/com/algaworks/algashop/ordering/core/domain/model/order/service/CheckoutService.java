package com.algaworks.algashop.ordering.core.domain.model.order.service;

import com.algaworks.algashop.ordering.core.domain.model.common.Money;
import com.algaworks.algashop.ordering.core.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.core.domain.model.order.specification.CustomerHaveFreeShippingSpecification;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.CreditCardId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.entity.ShoppingCartItem;
import com.algaworks.algashop.ordering.core.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.core.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.exception.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.core.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.Billing;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.Shipping;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@DomainService
@RequiredArgsConstructor
public class CheckoutService {

    private final CustomerHaveFreeShippingSpecification haveFreeShippingSpec;

    public Order checkout(ShoppingCart shoppingCart, Customer customer, Billing billingInfo,
                          Shipping shippingInfo, PaymentMethod paymentMethod, CreditCardId creditCardId) {

        if (shoppingCart.containsUnavailableItems() || shoppingCart.isEmpty()) {
            throw new ShoppingCartCantProceedToCheckoutException();
        }

        Set<ShoppingCartItem> items = shoppingCart.items();

        Order order = Order.draft(shoppingCart.customerId());

        order.changeBilling(billingInfo);

        if (haveFreeShipping(customer)) {
            Shipping freeShipping = shippingInfo.toBuilder().cost(Money.ZERO).build();
            order.changeShipping(freeShipping);
        } else {
            order.changeShipping(shippingInfo);
        }

        order.changePaymentMethod(paymentMethod, creditCardId);

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

    private boolean haveFreeShipping(Customer customer) {
        return haveFreeShippingSpec.isSatisfiedBy(customer);
    }
}
