package com.algaworks.algashop.ordering.infrastructure.rapidex.service;

import com.algaworks.algashop.ordering.domain.model.service.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.infrastructure.rapidex.client.RapiDexAPIClient;
import com.algaworks.algashop.ordering.infrastructure.rapidex.dtos.DeliveryCostRequest;
import com.algaworks.algashop.ordering.infrastructure.rapidex.dtos.DeliveryCostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "algashop.integrations.shipping.provider", havingValue = "RAPIDEX")
public class ShippingCostRapiDexImpl implements ShippingCostService {

    private final RapiDexAPIClient client;

    @Override
    public CalculationResult calculate(CalculationRequest request) {
        DeliveryCostResponse response = client.calculate(new DeliveryCostRequest(request.origin().value(), request.destination().value()));
        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(response.getEstimatedDaysToDeliver());
        return CalculationResult.builder()
                .cost(new Money(response.getDeliveryCost()))
                .expectedDate(expectedDeliveryDate)
                .build();
    }
}
