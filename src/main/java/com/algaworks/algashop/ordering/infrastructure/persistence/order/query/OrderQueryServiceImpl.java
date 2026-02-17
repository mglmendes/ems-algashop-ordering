package com.algaworks.algashop.ordering.infrastructure.persistence.order.query;

import com.algaworks.algashop.ordering.application.model.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.application.model.order.query.OrderQueryService;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.order.exceptions.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.valueobjects.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.repository.OrderPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderPersistenceEntityRepository orderPersistenceRepository;
    private final Mapper mapper;

    @Override
    public OrderDetailOutput findById(String orderId) {
        OrderPersistenceEntity orderPersistenceEntity = orderPersistenceRepository.findById(new OrderId(orderId).value().toLong()).orElseThrow(
                () -> new OrderNotFoundException(orderId)
        );
        return mapper.convert(orderPersistenceEntity, OrderDetailOutput.class);
    }
}
