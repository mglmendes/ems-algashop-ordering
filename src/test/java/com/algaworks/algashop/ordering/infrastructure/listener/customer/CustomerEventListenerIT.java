package com.algaworks.algashop.ordering.infrastructure.listener.customer;


import com.algaworks.algashop.ordering.core.application.model.AbstractApplicationIT;
import com.algaworks.algashop.ordering.core.ports.in.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.core.ports.out.customer.notifications.ForNotifyingCustomers;
import com.algaworks.algashop.ordering.core.ports.in.customer.ForAddingLoyaltyPoints;
import com.algaworks.algashop.ordering.core.domain.model.common.Email;
import com.algaworks.algashop.ordering.core.domain.model.common.FullName;
import com.algaworks.algashop.ordering.core.domain.model.customer.event.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.order.event.OrderReadyEvent;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.OrderId;
import com.algaworks.algashop.ordering.core.ports.out.customer.persistence.ForObtainingCustomers;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.listener.customer.CustomerEventListener;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.OffsetDateTime;
import java.util.UUID;

class CustomerEventListenerIT extends AbstractApplicationIT {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @MockitoBean
    private ForNotifyingCustomers forNotifyingCustomers;

    @MockitoBean
    private ForAddingLoyaltyPoints forAddingLoyaltyPoints;

    @MockitoBean
    private ForObtainingCustomers forObtainingCustomers;

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
        Mockito.verify(forAddingLoyaltyPoints).addLoyaltyPoints(Mockito.any(UUID.class), Mockito.any(String.class));
    }

    @Test
    public void shouldListenerCustomerRegisteredEvent() {
        Mockito.when(forObtainingCustomers.findById(ArgumentMatchers.any(UUID.class))).thenReturn(new CustomerOutput());
        applicationEventPublisher.publishEvent(
                new CustomerRegisteredEvent(
                        new CustomerId(),
                        OffsetDateTime.now(),
                        new FullName("Jhon", "Doe"),
                        new Email("jhon.due@email.com")
                )
        );

        Mockito.verify(customerEventListener).listen(Mockito.any(CustomerRegisteredEvent.class));
        Mockito.verify(forNotifyingCustomers).notifyNewRegistration(Mockito.any(ForNotifyingCustomers.NotifyNewRegistrationInput.class));
    }
}