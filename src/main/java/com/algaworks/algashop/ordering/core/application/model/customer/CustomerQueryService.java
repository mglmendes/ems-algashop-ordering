package com.algaworks.algashop.ordering.core.application.model.customer;

import com.algaworks.algashop.ordering.core.ports.in.customer.ForQueryingCustomers;
import com.algaworks.algashop.ordering.core.ports.in.customer.filter.CustomerFilter;
import com.algaworks.algashop.ordering.core.ports.in.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.core.ports.in.customer.output.CustomerSummaryOutput;
import com.algaworks.algashop.ordering.core.ports.out.customer.persistence.ForObtainingCustomers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerQueryService implements ForQueryingCustomers {

    private final ForObtainingCustomers forObtainingCustomers;

    @Override
    public CustomerOutput findById(UUID customerId) {
        return forObtainingCustomers.findById(customerId);
    }

    @Override
    public Page<CustomerSummaryOutput> filter(CustomerFilter filter) {
        return forObtainingCustomers.filter(filter);
    }
}
