package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.application.model.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.application.model.order.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderQueryService orderQueryService;

    @GetMapping("/{orderId}")
    public OrderDetailOutput findById(@PathVariable String orderId) {
        return orderQueryService.findById(orderId);
    }
}
