package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.order.exceptions.OrderCannotBeEditedException;
import com.algaworks.algashop.ordering.domain.model.order.exceptions.OrderDoesNotContainOrderItemException;
import com.algaworks.algashop.ordering.domain.model.order.entity.Order;
import com.algaworks.algashop.ordering.domain.model.order.entity.OrderItem;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderRemoveItemTest {

    @Test
    public void givenDraftOrder_whenTryingToRemoveItem_shouldRemoveItem() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderItem orderItem =order.items().iterator().next();

        Assertions.assertThat(order.items().size()).isEqualTo(2);
        order.removeItem(orderItem.id());
        Assertions.assertThat(order.items().size()).isEqualTo(1);
    }

    @Test
    public void givenPlacedOrder_whenTryingToRemoveItem_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class).isThrownBy(
                () -> order.removeItem(new OrderItemId())
        );
    }

    @Test
    public void givenDraftOrder_whenTryingToRemoveItemInexistent_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().build();
        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class).isThrownBy(
                () -> order.removeItem(new OrderItemId())
        );
    }
}
