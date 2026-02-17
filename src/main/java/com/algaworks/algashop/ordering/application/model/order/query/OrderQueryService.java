package com.algaworks.algashop.ordering.application.model.order.query;

import com.algaworks.algashop.ordering.application.model.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.application.model.order.output.OrderSummaryOutput;
import com.algaworks.algashop.ordering.application.utility.PageFilter;
import org.springframework.data.domain.Page;

public interface OrderQueryService {

    OrderDetailOutput findById(String orderId);

    Page<OrderSummaryOutput> filter(PageFilter filter);
}
