package com.algaworks.algashop.ordering.infrastructure.rapidex.dtos;

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
