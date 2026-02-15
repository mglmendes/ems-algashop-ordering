package com.algaworks.algashop.ordering.domain.event;

import java.util.List;

public interface DomainEventSource {
    List<Object> domainEvents();
    void clearDomainEvents();
}
