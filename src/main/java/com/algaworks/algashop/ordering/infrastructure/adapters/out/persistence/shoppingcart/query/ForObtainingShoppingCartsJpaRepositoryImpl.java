package com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.query;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.output.ShoppingCartOutput;
import com.algaworks.algashop.ordering.core.application.utility.Mapper;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.exception.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.core.ports.out.shoppingcart.ForObtainingShoppingCarts;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ForObtainingShoppingCartsJpaRepositoryImpl implements ForObtainingShoppingCarts {

    private final ShoppingCartPersistenceEntityRepository shoppingPersistenceRepository;
    private final Mapper mapper;

    @Override
    public ShoppingCartOutput findById(UUID shoppingCartId) {
        ShoppingCartPersistenceEntity shoppingCartPersistenceEntity = shoppingPersistenceRepository.findById(shoppingCartId).orElseThrow(
                () -> new ShoppingCartNotFoundException(shoppingCartId)
        );
        return mapper.convert(shoppingCartPersistenceEntity, ShoppingCartOutput.class);
    }

    @Override
    public ShoppingCartOutput findByCustomerId(UUID customerId) {
        ShoppingCartPersistenceEntity shoppingCartPersistenceEntity =
                shoppingPersistenceRepository.findByCustomer_Id(customerId).orElseThrow(
                        ShoppingCartNotFoundException::new
                );

        return mapper.convert(shoppingCartPersistenceEntity, ShoppingCartOutput.class);
    }
}
