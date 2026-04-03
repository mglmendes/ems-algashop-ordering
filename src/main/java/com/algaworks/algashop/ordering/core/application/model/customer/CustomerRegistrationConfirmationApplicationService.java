package com.algaworks.algashop.ordering.core.application.model.customer;

import com.algaworks.algashop.ordering.core.ports.in.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.core.ports.out.customer.notifications.ForNotifyingCustomers;
import com.algaworks.algashop.ordering.core.ports.out.customer.persistence.ForObtainingCustomers;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.customer.ForConfirmCustomerRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationConfirmationApplicationService implements ForConfirmCustomerRegistration {

    private final ForNotifyingCustomers forNotifyingCustomers;
    private final ForObtainingCustomers forObtainingCustomers;
    @Override
    public void confirm(UUID customerId) {
        CustomerOutput customerOutput = forObtainingCustomers.findById(customerId);
        var customerToBeNotified = new ForNotifyingCustomers.NotifyNewRegistrationInput(
                customerOutput.getId(),
                customerOutput.getFirstName(),
                customerOutput.getEmail());
        forNotifyingCustomers.notifyNewRegistration(
                customerToBeNotified);
    }
}
