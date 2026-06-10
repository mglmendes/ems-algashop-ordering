package com.algaworks.algashop.ordering.core.application.model.shipping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingCostPreviewOutput {
    private BigDecimal cost;
    private LocalDate expectedDate;
}
