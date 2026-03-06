package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.query;

import com.algaworks.algashop.ordering.application.model.shoppingcart.output.ShoppingCartOutput;
import com.algaworks.algashop.ordering.application.model.shoppingcart.query.ShoppingCartQueryService;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.exception.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.repository.ShoppingCartPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ShoppingCartQueryServiceImpl implements ShoppingCartQueryService {

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
