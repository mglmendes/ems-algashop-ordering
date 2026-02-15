package com.algaworks.algashop.ordering.infrastructure.listener.order;

import com.algaworks.algashop.ordering.domain.model.order.event.OrderCanceledEvent;
import com.algaworks.algashop.ordering.domain.model.order.event.OrderPaidEvent;
import com.algaworks.algashop.ordering.domain.model.order.event.OrderPlacedEvent;
import com.algaworks.algashop.ordering.domain.model.order.event.OrderReadyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventListener {

    @EventListener
    public void listen(OrderPlacedEvent event) {

    }

    @EventListener
    public void listen(OrderPaidEvent event) {

    }

    @EventListener
    public void listen(OrderReadyEvent event) {

    }

    @EventListener
    public void listen(OrderCanceledEvent event) {

    }

}