package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.databuilder.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    public void shouldGenerateBrandNewOrderItem() {
        OrderId orderId = new OrderId();
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();
        Quantity quantity = new Quantity(1);
        OrderItem orderItem = OrderItem.brandNew()
                .orderId(orderId)
                .product(product)
                .quantity(quantity)
                .build();

        Assertions.assertWith(orderItem,
                o -> Assertions.assertThat(o.id()).isNotNull(),
                o -> Assertions.assertThat(o.orderId()).isEqualTo(orderId),
                o -> Assertions.assertThat(o.quantity()).isEqualTo(quantity),
                o -> Assertions.assertThat(o.productId()).isEqualTo(product.id()),
                o -> Assertions.assertThat(o.quantity()).isEqualTo(quantity),
                o -> Assertions.assertThat(o.price()).isEqualTo(product.price())
        );
    }

}