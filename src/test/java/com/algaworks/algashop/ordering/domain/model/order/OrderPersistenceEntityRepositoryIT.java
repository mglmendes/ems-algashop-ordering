package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfiguration;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({SpringDataAuditingConfig.class, HibernateConfiguration.class})
class OrderPersistenceEntityRepositoryIT {

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

        Assertions.assertThat(entity.getCreatedByUSerId()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}