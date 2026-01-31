package com.algaworks.algashop.ordering.domain.factory;

import com.algaworks.algashop.ordering.domain.databuilder.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.databuilder.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.Shipping;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderFactoryTest {

    @Test
    public void shouldGenerateFilledOrderThatCanBePlaced() {
        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();

        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();
        PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

        Quantity quantity = new Quantity(1);
        CustomerId customerId = new CustomerId();

        Order orderFilled = OrderFactory.filled(
                customerId,
                shipping,
                billing,
                paymentMethod,
                product,
                quantity
        );

        Assertions.assertThat(orderFilled.isDraft()).isTrue();

        Assertions.assertWith(orderFilled,
                o -> Assertions.assertThat(o.shipping()).isEqualTo(shipping),
                o -> Assertions.assertThat(o.billing()).isEqualTo(billing),
                o -> Assertions.assertThat(o.paymentMethod()).isEqualTo(paymentMethod),
                o -> Assertions.assertThat(o.items()).isNotEmpty(),
                o -> Assertions.assertThat(o.customerId()).isEqualTo(customerId)
        );


        orderFilled.place();

        Assertions.assertThat(orderFilled.isPlaced()).isTrue();
    }
}
