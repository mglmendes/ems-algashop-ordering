package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.databuilder.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerate() {
        OrderItem.brandNew()
                .orderId(new OrderId())
                .product(ProductTestDataBuilder.aProductAltMousePad().build())
                .quantity(new Quantity(1))
                .build();
    }

}