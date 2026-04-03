package com.algaworks.algashop.ordering.core.application.model.order;

import com.algaworks.algashop.ordering.core.ports.in.order.ForQueryingOrders;
import com.algaworks.algashop.ordering.core.ports.in.order.filter.OrderFilter;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.core.ports.in.order.output.OrderSummaryOutput;
import com.algaworks.algashop.ordering.core.ports.out.order.ForObtainingOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService implements ForQueryingOrders {

    private final ForObtainingOrders forObtainingOrders;

    @Override
    public OrderDetailOutput findById(String orderId) {
        return forObtainingOrders.findById(orderId);
    }

    @Override
    public Page<OrderSummaryOutput> filter(OrderFilter filter) {
        return forObtainingOrders.filter(filter);
    }
}
