package com.algaworks.algashop.ordering.application.model.shoppingcart.service;

import com.algaworks.algashop.ordering.application.model.shoppingcart.input.ShoppingCartItemInput;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.exception.ProductNotFoundException;
import com.algaworks.algashop.ordering.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.exception.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.service.ShoppingService;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.valueobject.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.valueobject.ShoppingCartItemId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartManagementApplicationService {

    private final Customers customers;
    private final ShoppingCarts shoppingCarts;
    private final ProductCatalogService productCatalogService;
    private final ShoppingService shoppingService;

    @Transactional
    public UUID createNew(UUID rawCustomerId) {
        Objects.requireNonNull(rawCustomerId);
        CustomerId customerId = new CustomerId(rawCustomerId);
        ShoppingCart shoppingCart = shoppingService.startShopping(customerId);
        shoppingCarts.add(shoppingCart);
        return shoppingCart.id().value();
    }

    @Transactional
    public void addItem(ShoppingCartItemInput input) {
        Objects.requireNonNull(input);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(input.getShoppingCartId())).orElseThrow(
                () -> new ShoppingCartNotFoundException(input.getShoppingCartId())
        );

        Product product = productCatalogService.ofId(new ProductId(input.getProductId())).orElseThrow(
                () -> new ProductNotFoundException(input.getProductId())
        );

        shoppingCart.addItem(product, new Quantity(input.getQuantity()));
        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    public void removeItem(UUID shoppingCartId, UUID shoppingCartItemId) {
        Objects.requireNonNull(shoppingCartId);
        Objects.requireNonNull(shoppingCartItemId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(shoppingCartId)).orElseThrow(
                () -> new ShoppingCartNotFoundException(shoppingCartId)
        );

        shoppingCart.removeItem(new ShoppingCartItemId(shoppingCartItemId));
        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    public void empty(UUID shoppingCartId) {
        Objects.requireNonNull(shoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(shoppingCartId)).orElseThrow(
                () -> new ShoppingCartNotFoundException(shoppingCartId)
        );

        shoppingCart.empty();
        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    public void delete(UUID shoppingCartId) {
        Objects.requireNonNull(shoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(shoppingCartId)).orElseThrow(
                () -> new ShoppingCartNotFoundException(shoppingCartId)
        );
        shoppingCarts.remove(shoppingCart);


    }
}
