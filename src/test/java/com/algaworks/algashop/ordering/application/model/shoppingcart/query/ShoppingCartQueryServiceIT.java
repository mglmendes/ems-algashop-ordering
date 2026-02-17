package com.algaworks.algashop.ordering.application.model.shoppingcart.query;


import com.algaworks.algashop.ordering.application.model.shoppingcart.output.ShoppingCartOutput;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.repository.ShoppingCarts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ShoppingCartQueryServiceIT {

    @Autowired
    private ShoppingCartQueryService shoppingCartQueryService;

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

        ShoppingCartOutput shoppingCartOutput = shoppingCartQueryService.findById(shoppingCart.id().value());

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

        ShoppingCartOutput output = shoppingCartQueryService.findByCustomerId(customer.id().value());
        Assertions.assertWith(output,
                o -> Assertions.assertThat(o.getId()).isEqualTo(shoppingCart.id().value()),
                o -> Assertions.assertThat(o.getCustomerId()).isEqualTo(shoppingCart.customerId().value())
        );
    }

}