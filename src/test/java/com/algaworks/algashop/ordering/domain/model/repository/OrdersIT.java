package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.databuilder.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.databuilder.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfiguration;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.providers.CustomersPersistenceProvider;
import com.algaworks.algashop.ordering.infrastructure.persistence.providers.OrdersPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static com.algaworks.algashop.ordering.databuilder.CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        HibernateConfiguration.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class})
class OrdersIT {

    private final Orders orders;

    private final Customers customers;

    @BeforeEach
    void setUp() {
        if (!customers.exists(DEFAULT_CUSTOMER_ID)) {
            customers.add(
                    CustomerTestDataBuilder.existingCustomer().build()
            );
        }
    }

    @Autowired
    public OrdersIT(Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @Test
    public void shouldPersistAndFind() {
        Order originalOrder = OrderTestDataBuilder.anOrder().build();
        OrderId orderId = originalOrder.id();

        orders.add(originalOrder);

        Optional<Order> possibleOrder = orders.ofId(orderId);

        Assertions.assertThat(possibleOrder).isPresent();

        Order savedOrder = possibleOrder.get();

        Assertions.assertThat(savedOrder).satisfies(
                s -> Assertions.assertThat(s.id()).isEqualTo(orderId),
                s -> Assertions.assertThat(s.customerId()).isEqualTo(originalOrder.customerId()),
                s -> Assertions.assertThat(s.totalAmount()).isEqualTo(originalOrder.totalAmount()),
                s -> Assertions.assertThat(s.totalItems()).isEqualTo(originalOrder.totalItems()),
                s -> Assertions.assertThat(s.placedAt()).isEqualTo(originalOrder.placedAt()),
                s -> Assertions.assertThat(s.paidAt()).isEqualTo(originalOrder.paidAt()),
                s -> Assertions.assertThat(s.canceledAt()).isEqualTo(originalOrder.canceledAt()),
                s -> Assertions.assertThat(s.readyAt()).isEqualTo(originalOrder.readyAt()),
                s -> Assertions.assertThat(s.status()).isEqualTo(originalOrder.status()),
                s -> Assertions.assertThat(s.paymentMethod()).isEqualTo(originalOrder.paymentMethod())
        );
    }

    @Test
    public void shouldUpdateExistingOrder() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        orders.add(order);

        orders.ofId(order.id()).orElseThrow();
        order.markAsPaid();
        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(order.isPaid()).isTrue();
    }

    @Test
    public void shouldNotAllowStaleUpdate() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        Order orderT1 = orders.ofId(order.id()).orElseThrow();
        Order orderT2 = orders.ofId(order.id()).orElseThrow();

        orderT1.markAsPaid();
        orders.add(orderT1);

        orderT2.cancel();

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class).isThrownBy(
                () -> orders.add(orderT2)
        );

        Order savedOrder = orders.ofId(order.id()).orElseThrow();

        Assertions.assertThat(savedOrder.canceledAt()).isNull();
        Assertions.assertThat(savedOrder.paidAt()).isNotNull();
    }

    @Test
    public void shouldCountExistingOrders() {
        Assertions.assertThat(orders.count()).isZero();

        Order order1 = OrderTestDataBuilder.anOrder().build();
        Order order2 = OrderTestDataBuilder.anOrder().build();

        orders.add(order1);
        orders.add(order2);

        Assertions.assertThat(orders.count()).isEqualTo(2L);
    }

    @Test
    public void shouldReturnExistingOrder() {

        Order order1 = OrderTestDataBuilder.anOrder().build();

        orders.add(order1);

        Assertions.assertThat(orders.exists(order1.id())).isTrue();
        Assertions.assertThat(orders.exists(new OrderId())).isFalse();
    }

    @Test
    public void shouldListExistingOrdersByYears() {
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        var savedOrders = orders.placedByCustomerInYear(DEFAULT_CUSTOMER_ID, Year.now());

        Assertions.assertThat(savedOrders).isNotEmpty();
        Assertions.assertThat(savedOrders.size()).isEqualTo(2);

        List<Order> emptyOrders = orders.placedByCustomerInYear(DEFAULT_CUSTOMER_ID, Year.now().minusYears(1));

        Assertions.assertThat(emptyOrders).isEmpty();

        emptyOrders = orders.placedByCustomerInYear(new CustomerId(), Year.now());

        Assertions.assertThat(emptyOrders).isEmpty();

    }

    @Test
    public void shouldReturnSalesMetricsByCustomer() {
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Order order2 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        orders.add(order1);
        orders.add(order2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        Money expectedTotalAmount = order1.totalAmount().add(order2.totalAmount());

        Assertions.assertThat(orders.totalSoldForCustomer(DEFAULT_CUSTOMER_ID)).isEqualTo(expectedTotalAmount);
    }

    @Test
    public void shouldReturnQuantitySalesByCustomer() {
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Order order2 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        orders.add(order1);
        orders.add(order2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        long expectedQuantity = 2L;
        Assertions.assertThat(orders.salesQuantityByCustomerInYear(DEFAULT_CUSTOMER_ID, Year.now()))
                .isEqualTo(expectedQuantity);
    }


}