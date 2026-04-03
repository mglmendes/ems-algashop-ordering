package com.algaworks.algashop.ordering.core.application.model.customer.service;

import com.algaworks.algashop.ordering.core.ports.in.common.AddressData;
import com.algaworks.algashop.ordering.core.ports.in.customer.input.CustomerUpdateInput;

public class CustomerUpdateInputTestDataBuilder {

    public static CustomerUpdateInput.CustomerUpdateInputBuilder aCustomer() {
        return CustomerUpdateInput.builder()
                .firstName("Kleber")
                .lastName("Xavier")
                .phone("224-224-4444")
                .promotionNotificationsAllowed(true)
                .address(AddressData.builder()
                        .street("Av Anhaia Melo")
                        .complement("2054")
                        .number("42149")
                        .neighborhood("Vila Prudente")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode("54321")
                        .build());
    }
}
