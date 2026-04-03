package com.algaworks.algashop.ordering.core.ports.in.order;

import com.algaworks.algashop.ordering.core.ports.in.order.input.BuyNowInput;

public interface ForBuyingProduct {
    String buyNow(BuyNowInput input);
}
