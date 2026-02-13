package com.algaworks.algashop.ordering.application.model.checkout.input;

import com.algaworks.algashop.ordering.application.model.common.BillingData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BuyNowInput {
    private ShippingInput shipping;
    private BillingData billing;
    private UUID productId;
    private UUID customerId;
    private Integer quantity;
    private String paymentMethod;
}
