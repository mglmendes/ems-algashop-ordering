package com.algaworks.algashop.ordering.databuilder;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();
    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
    private Shipping shipping = aShipping();
    private Billing billing = aBilling();
    private boolean withItems = true;
    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {

    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShipping(shipping);
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));
            order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));
        }

        switch (this.status) {
            case DRAFT -> {}
            case PLACED -> {
                order.place();
            }
            case PAID -> {
                order.place();
                order.markAsPaid();
            }
            case READY -> {}
            case CANCELED -> {}
        }

        return order;
    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shipping(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billing(Billing billing) {
        this.billing = billing;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public static Shipping aShipping() {
        return Shipping.builder()
                .address(anAddress())
                .recipient(Recipient.builder()
                        .document(new Document("555-12-6654"))
                        .phone(new Phone("555-12-6654"))
                        .fullName(new FullName("Cleiton", "Rasta"))
                        .build())
                .cost(new Money("10"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .build();
    }

    public static Address anAddressAlt() {
        return Address.builder()
                .street("Sanson Street")
                .number("876")
                .neighborhood("Sansome")
                .city("San Francisco")
                .state("California")
                .zipCode(new ZipCode("12356")).build();
    }


    public static Billing aBilling() {
        return Billing.builder().address(anAddress()).document(new Document("555-12-6654"))
                .email(new Email("jhon.due@email.com"))
                .phone(new Phone("555-12-6654")).fullName(new FullName("Cleiton", "Rasta")).build();
    }

    public static Address anAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("10023")
                .neighborhood("Neighbor")
                .city("MonteFort")
                .state("State")
                .zipCode(new ZipCode("12356")).build();
    }
}
