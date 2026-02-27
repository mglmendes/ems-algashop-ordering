package com.algaworks.algashop.ordering.application.model.shoppingcart.output;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
public class ShoppingCartItemOutput {
  private UUID id;
  private UUID productId;
  private String name;
  private BigDecimal price;
  private Integer quantity;
  private BigDecimal totalAmount;
  private Boolean available;
}