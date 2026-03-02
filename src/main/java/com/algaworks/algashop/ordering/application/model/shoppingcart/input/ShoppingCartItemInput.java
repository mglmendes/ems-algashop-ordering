package com.algaworks.algashop.ordering.application.model.shoppingcart.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItemInput {
    @NotNull
    private Integer quantity;
    @NotNull
    private UUID productId;
    @NotNull
    private UUID shoppingCartId;
}