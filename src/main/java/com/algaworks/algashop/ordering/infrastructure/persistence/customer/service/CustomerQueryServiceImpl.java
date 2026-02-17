package com.algaworks.algashop.ordering.infrastructure.persistence.customer.service;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.repository.CustomerPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerPersistenceEntityRepository customerPersistenceRepository;
    private final Mapper mapper;

    @Override
    public CustomerOutput findById(UUID customerId) {
        Objects.requireNonNull(customerId);
        CustomerPersistenceEntity customer = customerPersistenceRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException(customerId)
        );

        return mapper.convert(customer, CustomerOutput.class);
    }
}
