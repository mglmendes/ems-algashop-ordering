package com.algaworks.algashop.ordering.application.service;

import com.algaworks.algashop.ordering.application.model.common.AddressData;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerUpdateInput;

import java.time.LocalDate;

public class CustomerInputTestDataBuilder {

    public static CustomerInput.CustomerInputBuilder aCustomer() {
        return CustomerInput.builder()
                .firstName("Miguel")
                .lastName("Mendes")
                .email("miguel@email.com")
                .document("555-222-1442")
                .phone("224-224-4444")
                .birthDate(LocalDate.of(2003, 8, 22))
                .promotionNotificationsAllowed(true)
                .address(AddressData.builder()
                        .street("Av Vila Ema")
                        .complement("2054")
                        .number("42149")
                        .neighborhood("Vila Prudente")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode("12345")
                        .build());
    }
}
