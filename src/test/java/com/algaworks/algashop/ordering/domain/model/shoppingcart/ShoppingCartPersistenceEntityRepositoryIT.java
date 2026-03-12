package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfiguration;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.entity.ShoppingCartPersistenceEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import({SpringDataAuditingConfig.class, HibernateConfiguration.class})
class ShoppingCartPersistenceEntityRepositoryIT {

    @Autowired
    private ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;

    @Autowired
    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    private CustomerPersistenceEntity customerPersistenceEntity;

    @BeforeEach
    void setUp() {
        shoppingCartPersistenceEntityRepository.deleteAll();
    }

    @Test
    public void shouldPersist() {
        CustomerId customerId = new CustomerId();
        customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
                CustomerPersistenceEntityTestDataBuilder.aCustomer().id(customerId.value()).email("jhondoe1@email.com").build()
        );
        ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();

        shoppingCartPersistenceEntityRepository.saveAndFlush(entity);
        Assertions.assertThat(shoppingCartPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        ShoppingCartPersistenceEntity savedEntity = shoppingCartPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        Assertions.assertThat(savedEntity.getItems()).isNotEmpty();
    }

    @Test
    public void shouldCount() {
        long shoppingCartsCount = shoppingCartPersistenceEntityRepository.count();
        Assertions.assertThat(shoppingCartsCount).isZero();
    }

    @Test
    public void shouldSetAuditingValues() {
        CustomerId customerId = new CustomerId();
        customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
                CustomerPersistenceEntityTestDataBuilder.aCustomer().id(customerId.value()).email("jhondoe2@email.com").build()
        );
        ShoppingCartPersistenceEntity entity = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();
        entity = shoppingCartPersistenceEntityRepository.saveAndFlush(entity);

        Assertions.assertThat(entity.getCreatedByUserId()).isNotNull();

        Assertions.assertThat(entity.getLastModifiedAt()).isNotNull();
        Assertions.assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}