package com.algaworks.algashop.ordering.application.model.customer.notifications;

import java.util.UUID;

public interface CustomerNotificationService {

    void notifyNewRegistration(NotifyNewRegistrationInput input);

    record NotifyNewRegistrationInput(String firstName, String email) {

    }
}
