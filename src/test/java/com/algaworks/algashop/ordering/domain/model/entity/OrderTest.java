package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.databuilder.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.databuilder.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.exception.OrderCannotBeEditedException;
import com.algaworks.algashop.ordering.domain.model.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.model.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.model.exception.ProductOutOfStockException;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.algaworks.algashop.ordering.domain.model.entity.enums.PaymentMethod.CREDIT_CARD;

class OrderTest {

    @Test
    public void shouldGenerateDraftOrder() {
        CustomerId customerId = new CustomerId();
        Order order = Order.draft(customerId);

        Assertions.assertWith(order,
                o -> Assertions.assertWith(o.id()).isNotNull(),
                o -> Assertions.assertThat(o.customerId()).isEqualTo(customerId),
                o -> Assertions.assertThat(o.totalAmount()).isEqualTo(Money.ZERO),
                o -> Assertions.assertThat(o.totalItems()).isEqualTo(Quantity.ZERO),
                o -> Assertions.assertThat(o.items()).isEmpty(),
                o -> Assertions.assertThat(o.isDraft()).isTrue()
                );

        Assertions.assertWith(order,
                o -> Assertions.assertWith(o.placedAt()).isNull(),
                o -> Assertions.assertWith(o.paidAt()).isNull(),
                o -> Assertions.assertWith(o.canceledAt()).isNull(),
                o -> Assertions.assertWith(o.readyAt()).isNull(),
                o -> Assertions.assertWith(o.billing()).isNull(),
                o -> Assertions.assertWith(o.shipping()).isNull(),
                o -> Assertions.assertWith(o.paymentMethod()).isNull()
        );
    }

    @Test
    public void shouldAddNewOrderItem() {
        Order order = Order.draft(new CustomerId());
        Product product = ProductTestDataBuilder.aProductAltMousePad().build();
        order.addItem(
                product,
                new Quantity(1));

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productName()).isNotNull(),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(product.id()),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemsSet() {
        Order order = Order.draft(new CustomerId());
        order.addItem(
                ProductTestDataBuilder.aProductAltMousePad().build(),
                new Quantity(1));
        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(
                items::clear
        );
    }

    @Test
    public void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());
        order.addItem(
                ProductTestDataBuilder.aProductAltMousePad().build(),
                new Quantity(2));

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("200"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(2));

        order.addItem(
                ProductTestDataBuilder.aProductAltRamMemory().build(),
                new Quantity(2));

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("600"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(4));

    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenTryingToPlace_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::place);
    }

    @Test
    public void givenPlacedOrder_whenPaid_shouldChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        order.markAsPaid();
        Assertions.assertThat(order.isPaid()).isTrue();
        Assertions.assertThat(order.paidAt()).isNotNull();
    }

    @Test
    public void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
        Order draft = Order.draft(new CustomerId());
        draft.changePaymentMethod(CREDIT_CARD);
        Assertions.assertThat(draft.paymentMethod()).isEqualTo(CREDIT_CARD);
    }

    @Test
    public void givenDraftOrder_whenChangeBilling_shouldAllowChange() {

        Billing billing = OrderTestDataBuilder.aBilling();

        Order draft = Order.draft(new CustomerId());

        draft.changeBilling(billing);

        Assertions.assertThat(draft.billing()).isEqualTo(billing);
    }

    @Test
    public void givenDraftOrder_whenChangeShipping_shouldAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("10023")
                .neighborhood("Neighboor")
                .city("MOnteFort")
                .state("State")
                .zipCode(new ZipCode("12356")).build();

        Shipping shipping = OrderTestDataBuilder.aShipping();

        Order draft = Order.draft(new CustomerId());

        draft.changeShipping(shipping);

        Assertions.assertThat(draft.shipping()).isEqualTo(shipping);
    }

    @Test
    public void givenDraftOrder_whenChangeShippingWithInvalidDeliveryDate_shouldNotAllowChange() {
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("10023")
                .neighborhood("Neighboor")
                .city("MOnteFort")
                .state("State")
                .zipCode(new ZipCode("12356")).build();

        Shipping shipping = OrderTestDataBuilder.aShipping().toBuilder()
                .expectedDate(LocalDate.now().minusWeeks(1))
                .build();

        Order draft = Order.draft(new CustomerId());



        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(() ->
                        draft.changeShipping(
                                shipping));
    }

    @Test
    public void givenDraftOrder_whenChangeItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());
        order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));

        OrderItem orderItem = order.items().iterator().next();
        order.changeItemQuantity(orderItem.id(), new Quantity(5));

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("1000")),
                (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(5)));
    }

    @Test
    public void givenProductOutOfStock_whenAddItem_shouldThrowException() {
        Order order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class).isThrownBy(
                () -> order.addItem(ProductTestDataBuilder.aProductUnavailable().build(), new Quantity(1))
        );
    }

    @Test
    public void giverPlacedOrder_whenTryingToAddItem_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(
                () -> order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1))
        );
    }

    @Test
    public void givenPaidOrder_whenReady_shouldMarkAsReady() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        order.markAsPaid();
        order.markAsReady();
        Assertions.assertThat(order.status()).isEqualTo(OrderStatus.READY);
    }

    @Test
    public void givenPlacedOrder_whenReady_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);
    }

    @Test
    public void givenDraftOrder_whenCancel_shouldCancelTheOrder() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.cancel();
        Assertions.assertThat(order.isCanceled()).isTrue();
    }

    @Test
    public void givenCanceledOrder_whenCancel_shouldCancelTheOrder() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.cancel();
        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::cancel);
    }
}