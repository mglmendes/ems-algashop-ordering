package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.databuilder.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.algaworks.algashop.ordering.domain.entity.enums.PaymentMethod.CREDIT_CARD;

class OrderTest {

    @Test
    public void shouldGenerate() {
        Order draft = Order.draft(new CustomerId());
    }

    @Test
    public void shouldAddNewOrderItem() {
        Order order = Order.draft(new CustomerId());
        ProductId productId = new ProductId();
        order.addItem(
                productId,
                new ProductName("Mouse Pad"),
                new Money("100"),
                new Quantity(1));

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items().size()).isEqualTo(1);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertWith(orderItem,
                (i) -> Assertions.assertThat(i.id()).isNotNull(),
                (i) -> Assertions.assertThat(i.productName()).isNotNull(),
                (i) -> Assertions.assertThat(i.productId()).isEqualTo(productId),
                (i) -> Assertions.assertThat(i.price()).isEqualTo(new Money("100")),
                (i) -> Assertions.assertThat(i.quantity()).isEqualTo(new Quantity(1))
        );
    }

    @Test
    public void shouldGenerateExceptionWhenTryToChangeItemsSet() {
        Order order = Order.draft(new CustomerId());
        order.addItem(
                new ProductId(),
                new ProductName("Mouse Pad"),
                new Money("100"),
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
                new ProductId(),
                new ProductName("Mouse Pad"),
                new Money("100"),
                new Quantity(2));

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("200"));
        Assertions.assertThat(order.totalItems()).isEqualTo(new Quantity(2));

        order.addItem(
                new ProductId(),
                new ProductName("RAM Memory"),
                new Money("400"),
                new Quantity(2));

        Assertions.assertThat(order.totalAmount()).isEqualTo(new Money("1000"));
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
        Address address = Address.builder()
                .street("Bourbon Street")
                .number("10023")
                .neighborhood("Neighboor")
                .city("MOnteFort")
                .state("State")
                .zipCode(new ZipCode("12356")).build();

        BillingInfo billingInfo = BillingInfo.builder().address(address).document(new Document("555-12-6654"))
                .phone(new Phone("555-12-6654")).fullName(new FullName("Cleiton", "Rasta")).build();

        Order draft = Order.draft(new CustomerId());

        draft.changeBilling(billingInfo);

        Assertions.assertThat(draft.billing()).isEqualTo(billingInfo);
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

        ShippingInfo shippingInfo = ShippingInfo.builder().address(address).document(new Document("555-12-6654"))
                .phone(new Phone("555-12-6654")).fullName(new FullName("Cleiton", "Rasta")).build();

        Order draft = Order.draft(new CustomerId());

        draft.changeShipping(shippingInfo, new Money("233"), LocalDate.of(2026, 02, 01));

        Assertions.assertThat(draft.shipping()).isEqualTo(shippingInfo);
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

        ShippingInfo shippingInfo = ShippingInfo.builder().address(address).document(new Document("555-12-6654"))
                .phone(new Phone("555-12-6654")).fullName(new FullName("Cleiton", "Rasta")).build();

        Order draft = Order.draft(new CustomerId());



        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(() ->
                        draft.changeShipping(
                                shippingInfo,
                                new Money("233"),
                                LocalDate.of(2025, 02, 01)));
    }

    @Test
    public void givenDraftOrder_whenChangeItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());
        order.addItem(new ProductId(), new ProductName("4GB RAM"), new Money("400.0"), new Quantity(1));

        OrderItem orderItem = order.items().iterator().next();
        order.changeItemQuantity(orderItem.id(), new Quantity(5));

        Assertions.assertWith(order,
                (o) -> Assertions.assertThat(o.totalAmount()).isEqualTo(new Money("2000")),
                (o) -> Assertions.assertThat(o.totalItems()).isEqualTo(new Quantity(5)));
    }
}