package com.algaworks.algashop.ordering.core.ports.out.customer.notifications;

import java.util.UUID;

public interface ForNotifyingCustomers {

    void notifyNewRegistration(NotifyNewRegistrationInput input);

    record NotifyNewRegistrationInput(UUID customerId, String firstName, String email) {

    }
}
