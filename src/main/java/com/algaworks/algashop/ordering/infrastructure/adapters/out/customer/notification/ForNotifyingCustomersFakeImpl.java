package com.algaworks.algashop.ordering.infrastructure.adapters.out.customer.notification;

import com.algaworks.algashop.ordering.core.ports.out.customer.notifications.ForNotifyingCustomers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForNotifyingCustomersFakeImpl implements ForNotifyingCustomers {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {
        log.info("Welcome {}", input.firstName());
        log.info("Use your email to access your account: {}", input.email());
    }
}
