package com.algaworks.algashop.ordering.application.model.shoppingcart.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Positive
    @NotNull
    private Integer quantity;
    @NotNull
    private UUID productId;

    private UUID shoppingCartId;
}