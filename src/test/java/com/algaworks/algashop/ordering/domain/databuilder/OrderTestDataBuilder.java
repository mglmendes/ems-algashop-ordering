package com.algaworks.algashop.ordering.domain.databuilder;

import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();
    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
    private Shipping shipping = aShipping();
    private Billing billing = aBillingInfo();
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

    public OrderTestDataBuilder shippingInfo(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billingInfo(Billing billing) {
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


    public static Billing aBillingInfo() {
        return Billing.builder().address(anAddress()).document(new Document("555-12-6654"))
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
