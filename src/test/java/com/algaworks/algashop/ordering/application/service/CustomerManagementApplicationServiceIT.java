package com.algaworks.algashop.ordering.application.service;

import com.algaworks.algashop.ordering.application.model.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerUpdateInput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.customer.service.CustomerManagementApplicationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@Transactional
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService applicationService;

    @Test
    public void shouldRegisterCustomer() {
        UUID customerId = applicationService.create(CustomerInputTestDataBuilder.aCustomer().build());

        Assertions.assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = applicationService.findById(customerId);

        Assertions.assertThat(customerOutput).extracting(
                CustomerOutput::getId,
                CustomerOutput::getFirstName,
                CustomerOutput::getLastName,
                CustomerOutput::getEmail,
                CustomerOutput::getBirthDate
        ).containsExactly(
                customerId,
                "Miguel",
                "Mendes",
                "miguel@email.com",
                LocalDate.of(2003, 8, 22)
        );
        Assertions.assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }

    @Test
    public void shouldUpdateCustomer() {
        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        CustomerUpdateInput customerUpdateInput = CustomerUpdateInputTestDataBuilder.aCustomer().build();
        UUID customerId = applicationService.create(customerInput);

        Assertions.assertThat(customerId).isNotNull();

        applicationService.update(customerId, customerUpdateInput);

        CustomerOutput customerOutput = applicationService.findById(customerId);

        Assertions.assertThat(customerOutput).extracting(
                CustomerOutput::getId,
                CustomerOutput::getFirstName,
                CustomerOutput::getLastName,
                CustomerOutput::getEmail,
                CustomerOutput::getBirthDate
        ).containsExactly(
                customerId,
                "Kleber",
                "Xavier",
                "miguel@email.com",
                LocalDate.of(2003, 8, 22)
        );
        Assertions.assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }
}