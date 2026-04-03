package com.algaworks.algashop.ordering.core.ports.in.order;

public interface ForManagingOrders {
    void cancel(String orderId);
    void markAsPaid(String orderId);
    void markAsReady(String orderId);


}
