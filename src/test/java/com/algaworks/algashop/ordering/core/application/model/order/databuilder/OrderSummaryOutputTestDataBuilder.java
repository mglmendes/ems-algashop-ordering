package com.algaworks.algashop.ordering.core.application.model.order.databuilder;

import com.algaworks.algashop.ordering.core.ports.in.order.output.CustomerMinimalOutput;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderSummaryOutput;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.OrderId;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderSummaryOutputTestDataBuilder {

    public static OrderSummaryOutput.OrderSummaryOutputBuilder placedOrder() {
        return OrderSummaryOutput.builder()
                .id(new OrderId().toString())
                .customer(CustomerMinimalOutput.builder()
                        .id(new CustomerId().value())
                        .firstName("John")
                        .lastName("Doe")
                        .document("12345")
                        .email("johndoe@email.com")
                        .phone("1191234564")
                        .build())
                .totalItems(2)
                .totalAmount(new BigDecimal("41.98"))
                .placedAt(OffsetDateTime.now())
                .paidAt(null)
                .canceledAt(null)
                .readyAt(null)
                .status("PLACED")
                .paymentMethod("GATEWAY_BALANCE");
    }

}