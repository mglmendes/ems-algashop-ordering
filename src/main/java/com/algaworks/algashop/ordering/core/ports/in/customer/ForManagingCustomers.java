package com.algaworks.algashop.ordering.core.ports.in.customer;

import com.algaworks.algashop.ordering.core.ports.in.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.core.ports.in.customer.input.CustomerUpdateInput;

import java.util.UUID;

public interface ForManagingCustomers {
    UUID create(CustomerInput input);
    void update(UUID rawCustomerId, CustomerUpdateInput input);
    void archive(UUID customerId);
    void changeEmail(UUID rawCustomerId, String newEmail);
}
