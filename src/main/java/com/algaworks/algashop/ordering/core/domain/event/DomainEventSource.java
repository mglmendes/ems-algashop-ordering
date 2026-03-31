package com.algaworks.algashop.ordering.core.domain.event;

import java.util.List;

public interface DomainEventSource {
    List<Object> domainEvents();
    void clearDomainEvents();
}
