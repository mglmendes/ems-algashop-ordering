package com.algaworks.algashop.ordering.application.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BillingData {
    private String firstName;
    private String lastName;
    private String document;
    private String phone;
    private String email;
    private AddressData address;
}
