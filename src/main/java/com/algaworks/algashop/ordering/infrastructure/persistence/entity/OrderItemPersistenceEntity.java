package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemPersistenceEntity {
    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;

    @ManyToOne(optional = false)
    @JoinColumn
    private OrderPersistenceEntity order;

    public Long getOrderId() {
        if (getOrder() == null) {
            return null;
        }
        return order.getId();
    }
}
