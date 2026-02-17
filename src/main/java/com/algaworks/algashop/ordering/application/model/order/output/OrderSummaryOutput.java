package com.algaworks.algashop.ordering.application.model.order.output;

import com.algaworks.algashop.ordering.domain.model.order.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSummaryOutput {
    private String id;
    private CustomerMinimalOutput customer;
    private Integer totalItems;
    private BigDecimal totalAmount;
    private OffsetDateTime placedAt;
    private OffsetDateTime readyAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime cancelAt;
    private String status;
    private String paymentMethod;

    public OrderSummaryOutput(Long id, Integer totalItems, BigDecimal totalAmount,
                              OffsetDateTime placedAt, OffsetDateTime readyAt, OffsetDateTime paidAt,
                              OffsetDateTime cancelAt, String status, String paymentMethod,
                              CustomerMinimalOutput customer) {
        this.id = new OrderId(id).toString();
        this.customer = customer;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
        this.placedAt = placedAt;
        this.readyAt = readyAt;
        this.paidAt = paidAt;
        this.cancelAt = cancelAt;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
