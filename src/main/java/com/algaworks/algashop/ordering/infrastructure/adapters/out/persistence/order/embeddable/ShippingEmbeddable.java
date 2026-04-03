package com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.order.embeddable;


import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.common.AddressEmbeddable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShippingEmbeddable {
    @Embedded
    private AddressEmbeddable address;
    @Embedded
    private RecipientEmbeddable recipient;
    private BigDecimal cost;
    private LocalDate expectedDate;
}
