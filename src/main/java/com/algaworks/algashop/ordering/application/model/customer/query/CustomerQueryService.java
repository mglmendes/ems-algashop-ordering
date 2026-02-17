package com.algaworks.algashop.ordering.application.model.customer.query;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;

import java.util.UUID;

public interface CustomerQueryService {

    CustomerOutput findById(UUID customerId);
}
