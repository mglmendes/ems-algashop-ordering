package com.algaworks.algashop.ordering.core.domain.model.generic;

import com.algaworks.algashop.ordering.core.domain.event.DomainEventSource;

public interface AggregateRoot<ID>  extends DomainEventSource {
    ID id();
}
