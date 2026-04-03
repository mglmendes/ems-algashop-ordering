package com.algaworks.algashop.ordering.core.application.model.shoppingcart;

import com.algaworks.algashop.ordering.core.domain.model.common.Quantity;
import com.algaworks.algashop.ordering.core.domain.model.customer.valueobjects.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.product.exception.ProductNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.product.service.ProductCatalogService;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.entity.ShoppingCart;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.exception.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.repository.ShoppingCarts;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.service.ShoppingService;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject.ShoppingCartId;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.valueobject.ShoppingCartItemId;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForManagingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.input.ShoppingCartItemInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartManagementApplicationService implements ForManagingShoppingCarts {

    private final ShoppingCarts shoppingCarts;
    private final ProductCatalogService productCatalogService;
    private final ShoppingService shoppingService;

    @Transactional
    @Override
    public UUID createNew(UUID rawCustomerId) {
        Objects.requireNonNull(rawCustomerId);
        CustomerId customerId = new CustomerId(rawCustomerId);
        ShoppingCart shoppingCart = shoppingService.startShopping(customerId);
        shoppingCarts.add(shoppingCart);
        return shoppingCart.id().value();
    }

    @Transactional
    @Override
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
    @Override
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
    @Override
    public void empty(UUID shoppingCartId) {
        Objects.requireNonNull(shoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(shoppingCartId)).orElseThrow(
                () -> new ShoppingCartNotFoundException(shoppingCartId)
        );

        shoppingCart.empty();
        shoppingCarts.add(shoppingCart);
    }

    @Transactional
    @Override
    public void delete(UUID shoppingCartId) {
        Objects.requireNonNull(shoppingCartId);
        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(shoppingCartId)).orElseThrow(
                () -> new ShoppingCartNotFoundException(shoppingCartId)
        );
        shoppingCarts.remove(shoppingCart);


    }
}
