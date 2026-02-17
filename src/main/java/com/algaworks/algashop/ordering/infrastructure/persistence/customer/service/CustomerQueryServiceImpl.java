package com.algaworks.algashop.ordering.infrastructure.persistence.customer.service;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.jpql.CustomerJPQLQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final EntityManager entityManager;

    @Override
    public CustomerOutput findById(UUID customerId) {
        Objects.requireNonNull(customerId);
        try {
            TypedQuery<CustomerOutput> query = entityManager.createQuery(CustomerJPQLQueries.findByIdAsOutputJPQL,
                    CustomerOutput.class);
            query.setParameter("id", customerId);
            return query.getSingleResult();
        } catch (NoResultException e) {
           throw new CustomerNotFoundException(customerId);
        }
    }
}
