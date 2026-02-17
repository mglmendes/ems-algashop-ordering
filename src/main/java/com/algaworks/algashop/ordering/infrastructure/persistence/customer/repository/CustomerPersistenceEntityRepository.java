package com.algaworks.algashop.ordering.infrastructure.persistence.customer.repository;

import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.query.CustomerPersistenceEntityQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistenceEntityRepository extends
        JpaRepository<CustomerPersistenceEntity, UUID>,
        CustomerPersistenceEntityQueries {
    Optional<CustomerPersistenceEntity> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID customerId);
}
