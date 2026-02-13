package com.algaworks.algashop.ordering.application.service;

import com.algaworks.algashop.ordering.application.management.customer.service.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.model.common.AddressData;
import com.algaworks.algashop.ordering.application.management.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.management.customer.output.CustomerOutput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService applicationService;

    @Test
    public void shouldRegisterCustomer() {
        UUID customerId = applicationService.create(CustomerInput.builder()
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
                        .build())
                .build());

        Assertions.assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = applicationService.findById(customerId);
        Assertions.assertThat(customerOutput.getId()).isEqualTo(customerId);
        Assertions.assertThat(customerOutput.getFirstName()).isEqualTo("Miguel");
        Assertions.assertThat(customerOutput.getLastName()).isEqualTo("Mendes");
        Assertions.assertThat(customerOutput.getEmail()).isEqualTo("miguel@email.com");
        Assertions.assertThat(customerOutput.getBirthDate()).isEqualTo(LocalDate.of(2003, 8, 22));
        Assertions.assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }
}