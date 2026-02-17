package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.infrastructure.persistence.order.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.entity.enums.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.common.Money;
import com.algaworks.algashop.ordering.domain.model.common.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    public void shouldConvertFromPersistence() {
        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        Order domainEntity = disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertThat(domainEntity).satisfies(
                s -> Assertions.assertThat(s.id()).isEqualTo(new OrderId(persistenceEntity.getId())),
                s -> Assertions.assertThat(s.customerId()).isEqualTo(new CustomerId(persistenceEntity.getCustomerId())),
                s -> Assertions.assertThat(s.totalAmount()).isEqualTo(new Money(persistenceEntity.getTotalAmount())),
                s -> Assertions.assertThat(s.totalItems()).isEqualTo(new Quantity(persistenceEntity.getTotalItems())),
                s -> Assertions.assertThat(s.status()).isEqualTo(OrderStatus.valueOf(persistenceEntity.getStatus())),
                s -> Assertions.assertThat(s.paymentMethod()).isEqualTo(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod())),
                s -> Assertions.assertThat(s.placedAt()).isEqualTo(persistenceEntity.getPlacedAt()),
                s -> Assertions.assertThat(s.paidAt()).isEqualTo(persistenceEntity.getPaidAt()),
                s -> Assertions.assertThat(s.canceledAt()).isEqualTo(persistenceEntity.getCanceledAt()),
                s -> Assertions.assertThat(s.readyAt()).isEqualTo(persistenceEntity.getReadyAt())
        );
    }
 }