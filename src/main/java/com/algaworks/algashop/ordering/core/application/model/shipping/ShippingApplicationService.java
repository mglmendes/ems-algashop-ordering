package com.algaworks.algashop.ordering.core.application.model.shipping;

import com.algaworks.algashop.ordering.core.domain.model.common.Address;
import com.algaworks.algashop.ordering.core.domain.model.common.ZipCode;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.core.domain.model.order.shipping.ShippingCostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShippingApplicationService {

    private final OriginAddressService originAddressService;
    private final ShippingCostService shippingCostService;

    public ShippingCostPreviewOutput previewCost(ShippingCostPreviewInput input) {
        Address originAddress = originAddressService.originAddress();
        var request = ShippingCostService.CalculationRequest.builder()
                .origin(originAddress.zipCode())
                .destination(new ZipCode(input.getZipCode()))
                .build();

        var response = shippingCostService.calculate(request);

        return new ShippingCostPreviewOutput(
                response.cost().value(), response.expectedDate()
        );
    }
}
