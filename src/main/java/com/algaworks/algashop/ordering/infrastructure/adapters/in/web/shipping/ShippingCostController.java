package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shipping;

import com.algaworks.algashop.ordering.core.application.model.shipping.ShippingApplicationService;
import com.algaworks.algashop.ordering.core.application.model.shipping.ShippingCostPreviewInput;
import com.algaworks.algashop.ordering.core.application.model.shipping.ShippingCostPreviewOutput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShippingCostController {

    private final ShippingApplicationService shippingApplicationService;

    @PostMapping("/api/v1/shipping-cost-previews")
    public ShippingCostPreviewOutput previewShippingCost(@Valid @RequestBody ShippingCostPreviewInput input) {
        return shippingApplicationService.previewCost(input);
    }
}
