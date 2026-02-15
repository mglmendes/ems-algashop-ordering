package com.algaworks.algashop.ordering.infrastructure.persistence.order.entity;

import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.embeddable.BillingEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = "id")
@Table(name = "\"order\"")
@EntityListeners(AuditingEntityListener.class)
public class OrderPersistenceEntity extends AbstractAggregateRoot<OrderPersistenceEntity> {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn
    private CustomerPersistenceEntity customer;

    private BigDecimal totalAmount;
    private Integer totalItems;

    private String status;
    private String paymentMethod;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    @CreatedBy
    private UUID createdByUSerId;
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @Version
    private Long version;

    @Embedded
    private BillingEmbeddable billing;

    @Embedded
    private ShippingEmbeddable shipping;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemPersistenceEntity> items = new HashSet<>();

    @Builder
    public OrderPersistenceEntity(Long id, CustomerPersistenceEntity customer, BigDecimal totalAmount, Integer totalItems, String status,
                                  String paymentMethod, OffsetDateTime placedAt, OffsetDateTime paidAt,
                                  OffsetDateTime canceledAt, OffsetDateTime readyAt, UUID createdByUSerId,
                                  OffsetDateTime lastModifiedAt, UUID lastModifiedByUserId, Long version,
                                  BillingEmbeddable billing, ShippingEmbeddable shipping,
                                  Set<OrderItemPersistenceEntity> items) {
        this.id = id;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.placedAt = placedAt;
        this.paidAt = paidAt;
        this.canceledAt = canceledAt;
        this.readyAt = readyAt;
        this.createdByUSerId = createdByUSerId;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedByUserId = lastModifiedByUserId;
        this.version = version;
        this.billing = billing;
        this.shipping = shipping;
        this.replaceItems(items);
    }

    public void replaceItems(Set<OrderItemPersistenceEntity> items) {
        if (items == null || items.isEmpty()) {
            this.setItems(new HashSet<>());
            return;
        }

        items.forEach(
                (item) -> item.setOrder(this)
        );
        this.setItems(items);
    }

    public void addItem(OrderItemPersistenceEntity item) {
        if (item == null) {
            return;
        }

        if (this.getItems() == null) {
            this.setItems(new HashSet<>());
        }

        item.setOrder(this);
        this.getItems().add(item);
    }

    public UUID getCustomerId() {
        if(this.customer == null) {
            return null;
        }

        return this.customer.getId();
    }

    public Collection<Object> getEvents() {
        return super.domainEvents();
    }

    public void addEvents(Collection<Object> events) {
        if (events != null) {
            events.forEach(
                    super::registerEvent
            );
        }
    }

}
