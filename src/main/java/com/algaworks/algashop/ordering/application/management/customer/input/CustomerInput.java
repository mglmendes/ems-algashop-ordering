package com.algaworks.algashop.ordering.application.management.customer.input;

import com.algaworks.algashop.ordering.application.model.common.AddressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInput {
    private String firstName;
    private String lastName;
    private String email;
    private String document;
    private String phone;
    private LocalDate birthDate;
    private Boolean promotionNotificationsAllowed;
    private AddressData address;
}
