package com.algaworks.algashop.ordering.domain.model.order.shipping;

import com.algaworks.algashop.ordering.domain.model.common.Money;
import com.algaworks.algashop.ordering.domain.model.common.ZipCode;
import lombok.Builder;

import java.time.LocalDate;

public interface ShippingCostService {
    CalculationResult calculate(CalculationRequest request);

    @Builder
    record CalculationRequest(ZipCode origin, ZipCode destination) {

    }

    @Builder
    record CalculationResult(Money cost, LocalDate expectedDate) {

    }
}
