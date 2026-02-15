package com.algaworks.algashop.ordering.domain.model.generic;

import com.algaworks.algashop.ordering.domain.event.DomainEventSource;

public interface AggregateRoot<ID>  extends DomainEventSource {
    ID id();
}
