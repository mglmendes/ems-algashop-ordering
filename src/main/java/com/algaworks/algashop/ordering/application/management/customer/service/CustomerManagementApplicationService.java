package com.algaworks.algashop.ordering.application.management.customer.service;

import com.algaworks.algashop.ordering.application.model.common.AddressData;
import com.algaworks.algashop.ordering.application.management.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.management.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.service.CustomerRegistrationService;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.BirthDate;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
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

    @Transactional(readOnly = true)
    public CustomerOutput findById(UUID customerId) {
        Objects.requireNonNull(customerId);
        Customer customer = customers.ofId(new CustomerId(customerId)).orElseThrow(
                () -> new CustomerNotFoundException(customerId)
        );

        return CustomerOutput.builder()
                .id(customer.id().value())
                .firstName(customer.fullName().firstName())
                .lastName(customer.fullName().lastName())
                .email(customer.email().value())
                .phone(customer.phone().value())
                .document(customer.document().value())
                .birthDate(customer.birthDate().value())
                .promotionNotificationsAllowed(customer.promotionNotificationsAllowed())
                .loyaltyPoints(customer.loyaltyPoints().value())
                .registeredAt(customer.registeredAt())
                .archivedAt(customer.archivedAt())
                .archived(customer.archived())
                .addressData(AddressData.builder()
                        .street(customer.address().street())
                        .complement(customer.address().complement())
                        .number(customer.address().number())
                        .neighborhood(customer.address().neighborhood())
                        .city(customer.address().city())
                        .state(customer.address().state())
                        .zipCode(customer.address().city()).build())
                .build();

    }
}
