package com.algaworks.algashop.ordering.application.model.order.query;


import com.algaworks.algashop.ordering.application.model.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.order.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.repository.Orders;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
class OrderQueryServiceIT {

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @Test
    void shouldFindOrderById() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder().customerId(customer.id()).build();
        orders.add(order);

        OrderDetailOutput orderDetailOutput = orderQueryService.findById(order.id().toString());

        Assertions.assertThat(orderDetailOutput).extracting(
                OrderDetailOutput::getId,
                OrderDetailOutput::getTotalAmount
        ).containsExactly(order.id().toString(), order.totalAmount().value());
    }

}