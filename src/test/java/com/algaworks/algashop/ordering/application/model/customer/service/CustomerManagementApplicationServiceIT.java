package com.algaworks.algashop.ordering.application.model.customer.service;

import com.algaworks.algashop.ordering.application.model.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.model.customer.input.CustomerUpdateInput;
import com.algaworks.algashop.ordering.application.model.customer.notifications.CustomerNotificationService;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.domain.model.customer.event.CustomerArchivedEvent;
import com.algaworks.algashop.ordering.domain.model.customer.event.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerEmailAlreadyInUseException;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.listener.customer.CustomerEventListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
@Transactional
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService applicationService;

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @MockitoSpyBean
    private CustomerNotificationService customerNotificationService;

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
        Mockito.verify(customerEventListener).listen(Mockito.any(CustomerRegisteredEvent.class));
        Mockito.verify(customerEventListener, Mockito.never()).listen(Mockito.any(CustomerArchivedEvent.class));
        Mockito.verify(customerNotificationService).notifyNewRegistration(Mockito.any(UUID.class));
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

    @Test
    public void shouldArchiveCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = applicationService.create(input);
        Assertions.assertThat(customerId).isNotNull();

        applicationService.archive(customerId);

        CustomerOutput archivedCustomer = applicationService.findById(customerId);

        Assertions.assertThat(archivedCustomer)
                .isNotNull()
                .extracting(
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getPhone,
                        CustomerOutput::getDocument,
                        CustomerOutput::getBirthDate,
                        CustomerOutput::getPromotionNotificationsAllowed
                ).containsExactly(
                        "Anonymous",
                        "Anonymous",
                        "000-000-0000",
                        "000-00-0000",
                        null,
                        false
                );

        Assertions.assertThat(archivedCustomer.getEmail()).endsWith("@anonymous.com");
        Assertions.assertThat(archivedCustomer.getArchived()).isTrue();
        Assertions.assertThat(archivedCustomer.getArchivedAt()).isNotNull();

        Assertions.assertThat(archivedCustomer.getAddress()).isNotNull();
        Assertions.assertThat(archivedCustomer.getAddress().getNumber()).isNotNull().isEqualTo("Anonymized");
        Assertions.assertThat(archivedCustomer.getAddress().getComplement()).isNull();
    }

    @Test
    public void shouldThrowCustomerNotFoundExceptionWhenArchivingNonExistingCustomer() {
        UUID nonExistingId = UUID.randomUUID();

        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> applicationService.archive(nonExistingId));
    }

    @Test
    public void shouldThrowCustomerArchivedExceptionWhenArchivingAlreadyIsArchivedCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = applicationService.create(input);
        Assertions.assertThat(customerId).isNotNull();

        applicationService.archive(customerId);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> applicationService.archive(customerId));
    }

    @Test
    void shouldChangeEmail() {
        String expectedEmail = "new.email@example.com";

        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = applicationService.create(customerInput);
        applicationService.changeEmail(customerId, expectedEmail);

        CustomerOutput customerOutput = applicationService.findById(customerId);

        Assertions.assertThat(customerOutput).isNotNull();
        Assertions.assertThat(customerOutput.getEmail())
                .isNotBlank()
                .isEqualTo(expectedEmail);
    }

    @Test
    void givenANonExistentCustomer_whenChangeEmail_shouldThrowException() {
        Assertions.assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> applicationService.changeEmail(
                        new CustomerId().value(),
                        "new.email@example.com"));
    }

    @Test
    void givenAnArchivedCustomer_whenChangeEmail_shouldThrowException() {
        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = applicationService.create(customerInput);
        applicationService.archive(customerId);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> applicationService.changeEmail(customerId, "new.email@example.com"));
    }

    @Test
    void givenAnInvalidEmail_whenChangeEmail_shouldThrowException() {
        CustomerInput customerInput = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = applicationService.create(customerInput);

        String invalidEmail = "email.com";

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> applicationService.changeEmail(customerId, invalidEmail));
    }

    @Test
    void givenAnAlreadyUsedEmail_whenChangeEmail_shouldThrowException() {
        CustomerInput customer1 = CustomerInputTestDataBuilder.aCustomer()
                .email("customer1@example.com")
                .build();
        UUID customerId1 = applicationService.create(customer1);

        CustomerInput customer2 = CustomerInputTestDataBuilder.aCustomer()
                .email("customer2@example.com")
                .build();
        applicationService.create(customer2);

        Assertions.assertThatExceptionOfType(CustomerEmailAlreadyInUseException.class)
                .isThrownBy(() -> applicationService.changeEmail(customerId1, customer2.getEmail()));
    }
}