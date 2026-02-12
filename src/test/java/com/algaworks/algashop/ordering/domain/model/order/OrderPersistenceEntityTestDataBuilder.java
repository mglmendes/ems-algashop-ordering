package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.embeddable.RecipientEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderPersistenceEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {

    }

    public static OrderPersistenceEntity.OrderPersistenceEntityBuilder existingOrder() {
          return OrderPersistenceEntity.builder()
                  .id(IdGenerator.generateTSID().toLong())
                  .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                  .totalAmount(new BigDecimal(2000))
                  .totalItems(6)
                  .status("DRAFT")
                  .items(Set.of(existingItem().build(), existingItemAlt().build()))
                  .shipping(ShippingEmbeddable.builder()
                          .cost(new BigDecimal(10))
                          .expectedDate(LocalDate.now().plusWeeks(1))
                          .address(address())
                          .recipient(RecipientEmbeddable.builder()
                                  .firstName("First Name")
                                  .lastName("Last Name")
                                  .document("Document")
                                  .phone("phone")
                                  .build())
                          .build())
                  .billing(BillingEmbeddable.builder()
                          .firstName("First Name")
                          .lastName("Last Name")
                          .document("Document")
                          .phone("Phone")
                          .email("test@email.com")
                          .address(address())
                          .build())
                  .paymentMethod("CREDIT_CARD");
    }

    private static AddressEmbeddable address() {
        return AddressEmbeddable.builder()
                .street("Street")
                .complement("Complement")
                .number("Number")
                .neighborhood("Neightb")
                .city("Cy")
                .state("ST")
                .zipCode("12345")
                .build();
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItem() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(500))
                .quantity(2)
                .totalAmount(new BigDecimal(1000))
                .productName("Notebook")
                .productId(IdGenerator.generateTimeBasedUUID());
    }

    public static OrderItemPersistenceEntity.OrderItemPersistenceEntityBuilder existingItemAlt() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .price(new BigDecimal(250))
                .quantity(4)
                .totalAmount(new BigDecimal(1000))
                .productName("Memoria Ram")
                .productId(IdGenerator.generateTimeBasedUUID());
    }
}
