package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.databuilder.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.databuilder.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.databuilder.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.enums.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.exception.CannotAddLoyaltyPointsToANonReadyOrder;
import com.algaworks.algashop.ordering.domain.model.exception.OrderNotBelongsToCustomerException;
import com.algaworks.algashop.ordering.domain.model.valueobject.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.model.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerLoyaltyPointsServiceTest {

    CustomerLoyaltyPointsService customerLoyaltyPointsService = new CustomerLoyaltyPointsService();

    @Test
    public void givenValidCustomerAndOrder_whenAddingPoints_ShouldAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    public void givenValidCustomerAndOrderWithLowValue_whenAddingPoints_ShouldNotAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        order.addItem(ProductTestDataBuilder.aProductAltRamMemory().build(), new Quantity(1));
        order.place();
        order.markAsPaid();
        order.markAsReady();
        customerLoyaltyPointsService.addPoints(customer, order);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(0));
    }

    @Test
    public void givenValidCustomerAndOtherCustomerOrder_whenAddingPoints_ShouldThrowOrderNotBelongsToCustomerException() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Order order = OrderTestDataBuilder.anOrder().customerId(new CustomerId()).build();
        order.place();
        order.markAsPaid();
        order.markAsReady();
        Assertions.assertThatExceptionOfType(
                OrderNotBelongsToCustomerException.class
        ).isThrownBy(() -> customerLoyaltyPointsService.addPoints(customer, order));
    }

    @Test
    public void givenValidCustomerAndValidNonReadyOrder_whenAddingPoints_ShouldThrowCannotAddLoyaltyPointsToANonReadyOrder() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        order.markAsPaid();
        Assertions.assertThatExceptionOfType(
                CannotAddLoyaltyPointsToANonReadyOrder.class
        ).isThrownBy(() -> customerLoyaltyPointsService.addPoints(customer, order));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();

        Assertions.assertThatNoException() //alterar
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new LoyaltyPoints(-10)); //simplificar
    }
}