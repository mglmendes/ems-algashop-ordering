package com.algaworks.algashop.ordering.core.ports.out.customer.persistence;

import com.algaworks.algashop.ordering.core.ports.in.customer.filter.CustomerFilter;
import com.algaworks.algashop.ordering.core.ports.in.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.core.ports.in.customer.output.CustomerSummaryOutput;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ForObtainingCustomers {

    CustomerOutput findById(UUID customerId);

    Page<CustomerSummaryOutput> filter(CustomerFilter filter);
}
