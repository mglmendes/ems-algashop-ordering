package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.AbstractPersistenceIT;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.repository.OrderPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class OrderPersistenceEntityRepositoryIT extends AbstractPersistenceIT {

    private final OrderPersistenceEntityRepository orderRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;
    private CustomerPersistenceEntity customerEntity;

    @Autowired
    OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderRepository,
                                       CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.orderRepository = orderRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    void setUp() {
        UUID value = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value();
        if (!customerPersistenceEntityRepository.existsById(value)) {
            customerEntity = customerPersistenceEntityRepository.save(
                    CustomerPersistenceEntityTestDataBuilder.aCustomer().build()
            );
        }
    }

    @Test
    public void shouldPersist() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder
                .existingOrder()
                .customer(customerEntity).build();
        orderRepository.saveAndFlush(entity);

        Assertions.assertThat(orderRepository.existsById(entity.getId())).isTrue();

        OrderPersistenceEntity savedEntity = orderRepository.findById(entity.getId()).orElseThrow();
        Assertions.assertThat(savedEntity.getItems()).isNotEmpty();

    }

    @Test
    public void shouldCount() {
        Assertions.assertThat(orderRepository.count()).isZero();
    }

    @Test
    public void shouldSetAuditingValues() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder
                .existingOrder()
                .customer(customerEntity)
                .build();
        entity = orderRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}