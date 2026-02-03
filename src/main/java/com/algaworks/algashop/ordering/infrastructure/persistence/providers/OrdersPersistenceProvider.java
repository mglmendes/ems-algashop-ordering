package com.algaworks.algashop.ordering.infrastructure.persistence.providers;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.repository.Orders;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository persistenceRepository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        Optional<OrderPersistenceEntity> possibleOrderEntity = persistenceRepository.findById(orderId.value().toLong());
        return possibleOrderEntity.map(
                disassembler::toDomainEntity
        );
    }

    @Override
    public boolean exists(OrderId orderId) {
        return false;
    }

    @Override
    public void add(Order aggregateRoot) {
        OrderPersistenceEntity entity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(entity);
    }

    @Override
    public int count() {
        return 0;
    }
}
