package com.algaworks.algashop.ordering.core.application.model.shoppingcart.query;


import com.algaworks.algashop.ordering.core.application.model.AbstractApplicationIT;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.core.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.core.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.output.ShoppingCartOutput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ShoppingCartQueryServiceIT extends AbstractApplicationIT {

    @Autowired
    private ForQueryingShoppingCarts forQueryingShoppingCarts;

    @Autowired
    private ShoppingCarts shoppingCarts;

    @Autowired
    private Customers customers;

    @Test
    void shouldFindShoppingCart() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().customerId(customer.id()).build();
        shoppingCarts.add(shoppingCart);

        ShoppingCartOutput shoppingCartOutput = forQueryingShoppingCarts.findById(shoppingCart.id().value());

        Assertions.assertThat(shoppingCartOutput).extracting(
                ShoppingCartOutput::getId,
                ShoppingCartOutput::getCustomerId,
                ShoppingCartOutput::getTotalAmount
        ).containsExactly(
                shoppingCart.id().value(),
                shoppingCart.customerId().value(),
                shoppingCart.totalAmount().value()
        );
    }

    @Test
    public void shouldFindByCustomerId() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        ShoppingCartOutput output = forQueryingShoppingCarts.findByCustomerId(customer.id().value());
        Assertions.assertWith(output,
                o -> Assertions.assertThat(o.getId()).isEqualTo(shoppingCart.id().value()),
                o -> Assertions.assertThat(o.getCustomerId()).isEqualTo(shoppingCart.customerId().value())
        );
    }

}