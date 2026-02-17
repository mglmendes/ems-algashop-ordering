package com.algaworks.algashop.ordering.application.model.order.query;

import com.algaworks.algashop.ordering.application.model.order.output.OrderDetailOutput;

public interface OrderQueryService {

    OrderDetailOutput findById(String orderId);
}
