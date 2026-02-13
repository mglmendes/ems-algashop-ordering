package com.algaworks.algashop.ordering.application.service;

import com.algaworks.algashop.ordering.application.model.data.AddressData;
import com.algaworks.algashop.ordering.application.model.input.CustomerInput;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.service.CustomerRegistrationService;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.BirthDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerManagementApplicationService {

    private final CustomerRegistrationService customerRegistration;

    private final Customers customers;

    @Transactional
    public UUID create(CustomerInput input) {
        Objects.requireNonNull(input);
        AddressData addressData = input.getAddress();
        Customer customer = customerRegistration.register(
                new FullName(input.getFirstName(), input.getLastName()),
                new BirthDate(input.getBirthDate()),
                new Email(input.getEmail()),
                new Phone(input.getPhone()),
                new Document(input.getDocument()),
                input.getPromotionNotificationsAllowed(),
                Address.builder()
                        .street(addressData.getStreet())
                        .complement(addressData.getComplement())
                        .number(addressData.getNumber())
                        .neighborhood(addressData.getNeighborhood())
                        .city(addressData.getCity())
                        .state(addressData.getState())
                        .zipCode(new ZipCode(addressData.getZipCode()))
                        .build()
        );
        customers.add(customer);
        return customer.id().value();
    }
}
