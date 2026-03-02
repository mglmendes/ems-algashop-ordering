package com.algaworks.algashop.ordering.application.model.checkout.input;

import com.algaworks.algashop.ordering.application.model.common.AddressData;
import com.algaworks.algashop.ordering.application.model.common.RecipientData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShippingInput {
    @Valid
    @NotNull
    private RecipientData recipient;
    @Valid
    @NotNull
    private AddressData address;
}
