package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.AbstractPersistenceIT;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ShoppingCartPersistenceEntityRepositoryIT extends AbstractPersistenceIT {

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