package com.algaworks.algashop.ordering.domain.model.customer.repository;

import com.algaworks.algashop.ordering.domain.model.generic.Repository;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.common.Email;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;

import java.util.Optional;

public interface Customers extends Repository<Customer, CustomerId> {

    Optional<Customer> ofEmail(Email email);

    boolean isEmailUnique(Email email, CustomerId execptCustomerId);
}
