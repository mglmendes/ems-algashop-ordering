package com.algaworks.algashop.ordering.infrastructure.adapters.out.customer.repository;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.customer.entity.CustomerPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends JpaRepository<CustomerPersistenceEntity, UUID> {
    Optional<CustomerPersistenceEntity> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID customerId);
}
