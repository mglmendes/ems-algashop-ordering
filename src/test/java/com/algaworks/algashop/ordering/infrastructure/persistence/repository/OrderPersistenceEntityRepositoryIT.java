package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.databuilder.OrderPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderRepository;

    @Autowired
    OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Test
    public void shouldPersist() {
        long orderId = IdGenerator.generateTSID().toLong();
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        orderRepository.saveAndFlush(entity);

        Assertions.assertThat(orderRepository.existsById(orderId)).isTrue();
    }

    @Test
    public void shouldCount() {
        Assertions.assertThat(orderRepository.count()).isZero();
    }

}