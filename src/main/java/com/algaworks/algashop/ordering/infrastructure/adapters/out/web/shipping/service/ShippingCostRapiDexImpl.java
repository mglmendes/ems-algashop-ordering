package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.service;

import com.algaworks.algashop.ordering.core.domain.model.common.Money;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.client.ResilientRapiDexApiClient;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.dtos.DeliveryCostRequest;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.dtos.DeliveryCostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "algashop.integrations.shipping.provider", havingValue = "RAPIDEX")
public class ShippingCostRapiDexImpl implements ShippingCostService {

    private final ResilientRapiDexApiClient resilientRapiDexApiClient;

    @Override
    public CalculationResult calculate(CalculationRequest request) {
        DeliveryCostResponse response = resilientRapiDexApiClient.calculate(
                new DeliveryCostRequest(
                        request.origin().value(), request.destination().value())
                );
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(response.getEstimatedDaysToDeliver());
        return CalculationResult.builder()
                .cost(new Money(response.getDeliveryCost()))
                .expectedDate(expectedDeliveryDate)
                .build();
    }
}
