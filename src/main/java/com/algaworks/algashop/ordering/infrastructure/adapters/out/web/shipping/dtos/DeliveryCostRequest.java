package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryCostRequest {
    private String originZipCode;
    private String destinationZipCode;
}
