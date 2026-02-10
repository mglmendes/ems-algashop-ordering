package com.algaworks.algashop.ordering.infrastructure.persistence.providers;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomersPersistenceProvider implements Customers {

    private final CustomerPersistenceEntityRepository persistenceRepository;
    private final CustomerPersistenceEntityAssembler assembler;
    private final CustomerPersistenceEntityDisassembler disassembler;

    private final EntityManager entityManager;


    @Override
    public Optional<Customer> ofId(CustomerId customerId) {
        Optional<CustomerPersistenceEntity> possibleEntity = persistenceRepository.findById(customerId.value());
        return possibleEntity.map(
                disassembler::toDomainEntity
        );

    }

    @Override
    public boolean exists(CustomerId customerId) {
        return persistenceRepository.existsById(customerId.value());
    }

    @Transactional(readOnly = false)
    @Override
    public void add(Customer aggregateRoot) {
        UUID orderId = aggregateRoot.id().value();

        persistenceRepository.findById(orderId).ifPresentOrElse(
                (persistenceEntity )-> {
                    update(aggregateRoot, persistenceEntity);
                },
                () -> {
                    insert(aggregateRoot);
                }
        );
    }

    @Override
    public long count() {
        return persistenceRepository.count();
    }

    private void update(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        entityManager.detach(persistenceEntity);
        persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    private void insert(Customer aggregateRoot) {
        CustomerPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
        updateVersion(aggregateRoot, persistenceEntity);
    }

    @SneakyThrows
    private void updateVersion(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity) {
        Field version = aggregateRoot.getClass().getDeclaredField("version");
        version.setAccessible(true);
        ReflectionUtils.setField(version, aggregateRoot, persistenceEntity.getVersion());
        version.setAccessible(false);
    }
}
