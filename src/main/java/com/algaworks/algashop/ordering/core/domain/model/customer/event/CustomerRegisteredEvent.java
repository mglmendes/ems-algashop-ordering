package com.algaworks.algashop.ordering.core.domain.model.customer.event;

import com.algaworks.algashop.ordering.core.domain.model.common.Email;
import com.algaworks.algashop.ordering.core.domain.model.common.FullName;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;

import java.time.OffsetDateTime;

public record CustomerRegisteredEvent(
        CustomerId customerId,
        OffsetDateTime registeredAt,
        FullName fullName,
        Email email
) {
}
