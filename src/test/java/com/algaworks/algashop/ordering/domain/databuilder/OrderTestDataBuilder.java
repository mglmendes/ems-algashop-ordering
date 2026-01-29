package com.algaworks.algashop.ordering.domain.databuilder;

import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();
    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;
    private Money shippingCost = new Money("10.0");
    private LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);
    private ShippingInfo shippingInfo = aShippingInfo();
    private BillingInfo billingInfo = aBillingInfo();
    private boolean withItems = true;
    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {

    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);
        order.changeBilling(billingInfo);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(new ProductId(), new ProductName("Notebook"), new Money("3000.0"), new Quantity(2));
            order.addItem(new ProductId(), new ProductName("4GB RAM"), new Money("400.0"), new Quantity(1));
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

    public OrderTestDataBuilder shippingCost(Money shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderTestDataBuilder expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public OrderTestDataBuilder shippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public OrderTestDataBuilder billingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
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

    public static ShippingInfo aShippingInfo() {
        return ShippingInfo.builder().address(anAddress()).document(new Document("555-12-6654"))
                .phone(new Phone("555-12-6654")).fullName(new FullName("Cleiton", "Rasta")).build();
    }


    public static BillingInfo aBillingInfo() {
        return BillingInfo.builder().address(anAddress()).document(new Document("555-12-6654"))
                .phone(new Phone("555-12-6654")).fullName(new FullName("Cleiton", "Rasta")).build();
    }

    public static Address anAddress() {
        return Address.builder()
                .street("Bourbon Street")
                .number("10023")
                .neighborhood("Neighbor")
                .city("MOnteFort")
                .state("State")
                .zipCode(new ZipCode("12356")).build();
    }
}
