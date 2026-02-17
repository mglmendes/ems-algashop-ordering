package com.algaworks.algashop.ordering.infrastructure.persistence.customer.query;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;

import java.util.Optional;
import java.util.UUID;

public interface CustomerPersistenceEntityQueries {

    Optional<CustomerOutput> findByIdAsOutput(UUID customerId);
}
