package com.algaworks.algashop.ordering.core.domain.model.shoppingcart.repository;

import com.algaworks.algashop.ordering.core.domain.model.generic.RemoveCapableRepository;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject.ShoppingCartId;

import java.util.Optional;

public interface ShoppingCarts extends RemoveCapableRepository<ShoppingCart, ShoppingCartId> {

    Optional<ShoppingCart> ofCustomer(CustomerId customerId);

}
