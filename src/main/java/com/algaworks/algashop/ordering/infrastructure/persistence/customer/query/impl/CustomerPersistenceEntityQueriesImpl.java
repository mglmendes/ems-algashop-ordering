package com.algaworks.algashop.ordering.infrastructure.persistence.customer.query.impl;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.domain.model.customer.jpql.CustomerJPQLQueries;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.query.CustomerPersistenceEntityQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomerPersistenceEntityQueriesImpl implements CustomerPersistenceEntityQueries {

    private final EntityManager entityManager;

    @Override
    public Optional<CustomerOutput> findByIdAsOutput(UUID customerId) {
        try {
            TypedQuery<CustomerOutput> query = entityManager.createQuery(CustomerJPQLQueries.findByIdAsOutputJPQL,
                    CustomerOutput.class);
            query.setParameter("id", customerId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }
}
