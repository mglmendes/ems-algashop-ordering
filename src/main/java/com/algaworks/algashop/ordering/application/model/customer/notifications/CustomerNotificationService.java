package com.algaworks.algashop.ordering.application.model.customer.notifications;

import java.util.UUID;

public interface CustomerNotificationService {

    void notifyNewRegistration(UUID customerId);
}
