package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.provider;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.service.ShoppingCartProductAdjustmentService;
import com.algaworks.algashop.ordering.domain.model.common.Money;
import com.algaworks.algashop.ordering.domain.model.product.valueobject.ProductId;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ShoppingCartUpdateProvider implements ShoppingCartProductAdjustmentService {

    private final ShoppingCartPersistenceEntityRepository shoppingCartRepository;

    @Override
    @Transactional
    public void adjustPrice(ProductId productId, Money updatedPrice) {
        shoppingCartRepository.updateItemPrice(productId.value(), updatedPrice.value());
        shoppingCartRepository.recalculateTotalsForCartsWithProduct(productId.value());
    }

    @Override
    @Transactional
    public void changeAvailability(ProductId productId, boolean available) {
        shoppingCartRepository.updateItemAvailability(productId.value(), available);
    }
}
