package com.algaworks.algashop.ordering.core.ports.in.order;

import com.algaworks.algashop.ordering.core.ports.in.order.filter.OrderFilter;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderSummaryOutput;
import org.springframework.data.domain.Page;

public interface ForQueryingOrders {

    OrderDetailOutput findById(String orderId);

    Page<OrderSummaryOutput> filter(OrderFilter filter);
}
