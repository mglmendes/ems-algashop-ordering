package com.algaworks.algashop.ordering.core.domain.model.customer.event;

import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;

import java.time.OffsetDateTime;

public record CustomerArchivedEvent(
        CustomerId customerId,
        OffsetDateTime archivedAt
) {
}
