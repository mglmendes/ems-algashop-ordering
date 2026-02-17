package com.algaworks.algashop.ordering.infrastructure.listener.customer;


import com.algaworks.algashop.ordering.application.model.customer.notifications.CustomerNotificationService;
import com.algaworks.algashop.ordering.application.model.customer.service.CustomerLoyaltyPointsApplicationService;
import com.algaworks.algashop.ordering.domain.model.common.Email;
import com.algaworks.algashop.ordering.domain.model.common.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.event.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.event.OrderReadyEvent;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.OffsetDateTime;
import java.util.UUID;

@SpringBootTest
class CustomerEventListenerIT {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @MockitoBean
    private CustomerNotificationService customerNotificationService;

    @MockitoBean
    private CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @Test
    void shouldListenerOrderReadyEvent() {
        applicationEventPublisher.publishEvent(
                new OrderReadyEvent(
                        new OrderId(),
                        new CustomerId(),
                        OffsetDateTime.now()
                )
        );

        Mockito.verify(customerEventListener).listen(Mockito.any(OrderReadyEvent.class));
        Mockito.verify(customerLoyaltyPointsApplicationService).addLoyaltyPoints(Mockito.any(UUID.class), Mockito.any(String.class));
    }

    @Test
    public void shouldListenerCustomerRegisteredEvent() {
        applicationEventPublisher.publishEvent(
                new CustomerRegisteredEvent(
                        new CustomerId(),
                        OffsetDateTime.now(),
                        new FullName("Jhon", "Doe"),
                        new Email("jhon.due@email.com")
                )
        );

        Mockito.verify(customerEventListener).listen(Mockito.any(CustomerRegisteredEvent.class));
        Mockito.verify(customerNotificationService).notifyNewRegistration(Mockito.any(CustomerNotificationService.NotifyNewRegistrationInput.class));
    }
}