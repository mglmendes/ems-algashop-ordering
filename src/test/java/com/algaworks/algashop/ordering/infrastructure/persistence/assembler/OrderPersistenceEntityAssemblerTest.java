package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.databuilder.OrderPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.databuilder.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class OrderPersistenceEntityAssemblerTest {

    private final OrderPersistenceEntityAssembler assembler = new OrderPersistenceEntityAssembler();

    @Test
    public void shouldConvertToDomain() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity entity = assembler.fromDomain(order);

        assertThat(entity).satisfies(
                p -> assertThat(p.getId()).isEqualTo(order.id().value().toLong()),
                p -> assertThat(p.getCustomerId()).isEqualTo(order.customerId().value()),
                p -> assertThat(p.getTotalAmount()).isEqualTo(order.totalAmount().value()),
                p -> assertThat(p.getTotalItems()).isEqualTo(order.totalItems().value()),
                p -> assertThat(p.getStatus()).isEqualTo(order.status().name()),
                p -> assertThat(p.getPaymentMethod()).isEqualTo(order.paymentMethod().name()),
                p -> assertThat(p.getPlacedAt()).isEqualTo(order.placedAt()),
                p -> assertThat(p.getPaidAt()).isEqualTo(order.paidAt()),
                p -> assertThat(p.getCanceledAt()).isEqualTo(order.canceledAt()),
                p -> assertThat(p.getReadyAt()).isEqualTo(order.readyAt())
        );
    }

    @Test
    public void givenOrderWithoutItems_shouldRemovePersistenceEntityItems() {
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        OrderPersistenceEntity orderEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(orderEntity.getItems()).isNotEmpty();

        assembler.merge(orderEntity, order);

        Assertions.assertThat(orderEntity.getItems()).isEmpty();
    }

    @Test
    public void givenOrderWithItems_shouldAddToPersistenceEntity() {
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        OrderPersistenceEntity orderEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().items(new HashSet<>()).build();

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(orderEntity.getItems()).isEmpty();

        assembler.merge(orderEntity, order);

        Assertions.assertThat(orderEntity.getItems()).isNotEmpty();
        Assertions.assertThat(orderEntity.getItems().size()).isEqualTo(order.items().size());
    }

    @Test
    public void givenOrderWithItems_whenMerge_shouldMergeCorrectly() {
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();

        var orderItemPersistenceEntities = order.items().stream().map(
                assembler::fromDomain
        ).collect(Collectors.toSet());

        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .items(orderItemPersistenceEntities)
                .build();

        order.removeItem(order.items().iterator().next().id());

        assembler.merge(persistenceEntity, order);

        Assertions.assertThat(persistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems().size()).isEqualTo(order.items().size());
    }
}