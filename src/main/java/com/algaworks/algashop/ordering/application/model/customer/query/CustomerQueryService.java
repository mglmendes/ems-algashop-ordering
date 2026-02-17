package com.algaworks.algashop.ordering.application.model.customer.query;

import com.algaworks.algashop.ordering.application.model.customer.filter.CustomerFilter;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerSummaryOutput;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CustomerQueryService {

    CustomerOutput findById(UUID customerId);

    Page<CustomerSummaryOutput> filter(CustomerFilter filter);
}
