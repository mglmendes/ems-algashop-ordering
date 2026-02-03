package com.algaworks.algashop.ordering.databuilder;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;

import java.math.BigDecimal;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {

    }

    public static OrderPersistenceEntity.OrderPersistenceEntityBuilder existingOrder() {
          return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customerId(IdGenerator.generateTimeBasedUUID())
                .totalAmount(new BigDecimal(1000))
                .totalItems(2)
                .status("DRAFT")
                .paymentMethod("CREDIT_CARD");
    }
}
