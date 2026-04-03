package com.algaworks.algashop.ordering.core.ports.in.order;

import com.algaworks.algashop.ordering.core.ports.in.order.input.CheckoutInput;

public interface ForBuyingWithShoppingCart {
    String checkout(CheckoutInput input);
}
