package com.algaworks.algashop.ordering.application.model.customer.service;

import com.algaworks.algashop.ordering.application.model.common.AddressData;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerUpdateInput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerArchivedException;
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

    private final Mapper mapper;

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
                getAddressByAddressData(addressData)
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

        return mapper.convert(customer, CustomerOutput.class);

    }

    @Transactional
    public void update(UUID rawCustomerId, CustomerUpdateInput input) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(rawCustomerId);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId)).orElseThrow(
                () -> new CustomerNotFoundException(rawCustomerId)
        );

        customer.changeName(new FullName(input.getFirstName(), input.getLastName()));
        customer.changePhone(new Phone(input.getPhone()));

        if (Boolean.TRUE.equals(input.getPromotionNotificationsAllowed())) {
            customer.enablePromotionNotifications();
        } else {
            customer.disablePromotionNotifications();
        }

        AddressData addressData = input.getAddress();
        customer.changeAddress(getAddressByAddressData(addressData));
        customers.add(customer);
    }

    private static Address getAddressByAddressData(AddressData addressData) {
        return Address.builder()
                .street(addressData.getStreet())
                .complement(addressData.getComplement())
                .number(addressData.getNumber())
                .neighborhood(addressData.getNeighborhood())
                .city(addressData.getCity())
                .state(addressData.getState())
                .zipCode(new ZipCode(addressData.getZipCode()))
                .build();
    }

    @Transactional
    public void archive(UUID customerId) {
        Objects.requireNonNull(customerId);
        Customer customer = customers.ofId(new CustomerId(customerId)).orElseThrow(
                () -> new CustomerNotFoundException(customerId)
        );

        if (customer.isArchived()) {
            throw new CustomerArchivedException(customerId);
        }

        customer.archive();
        customers.add(customer);
    }

    @Transactional
    public void changeEmail(UUID rawCustomerId, String newEmail) {
        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(newEmail);
        Customer customer = customers.ofId(new CustomerId(rawCustomerId))
                .orElseThrow(()-> new CustomerNotFoundException(rawCustomerId));
        customerRegistration.changeEmail(customer, new Email(newEmail));
        customers.add(customer);
    }
}
