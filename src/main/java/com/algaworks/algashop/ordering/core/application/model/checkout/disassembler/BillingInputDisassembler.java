package com.algaworks.algashop.ordering.core.application.model.checkout.disassembler;

import com.algaworks.algashop.ordering.core.application.model.common.AddressData;
import com.algaworks.algashop.ordering.core.application.model.common.BillingData;
import com.algaworks.algashop.ordering.core.domain.model.common.*;
import com.algaworks.algashop.ordering.core.domain.model.common.*;
import com.algaworks.algashop.ordering.core.domain.model.order.valueobjects.Billing;
import org.springframework.stereotype.Component;

@Component
public class BillingInputDisassembler {

    public Billing toDomainModel(BillingData billingData) {
        AddressData address = billingData.getAddress();
        return Billing.builder()
                .fullName(new FullName(billingData.getFirstName(), billingData.getLastName()))
                .document(new Document(billingData.getDocument()))
                .phone(new Phone(billingData.getPhone()))
                .email(new Email(billingData.getEmail()))
                .address(Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build())
                .build();
    }
}