package com.algaworks.algashop.ordering.presentation;

import com.algaworks.algashop.ordering.application.model.customer.input.CustomerInput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@RequestBody CustomerInput customerInput) {

        return CustomerOutput.builder()
                .id(UUID.randomUUID())
                .firstName(customerInput.getFirstName())
                .lastName(customerInput.getLastName())
                .email(customerInput.getEmail())
                .phone(customerInput.getPhone())
                .birthDate(customerInput.getBirthDate())
                .document(customerInput.getDocument())
                .promotionNotificationsAllowed(customerInput.getPromotionNotificationsAllowed())
                .registeredAt(OffsetDateTime.now())
                .archived(false)
                .loyaltyPoints(0)
                .address(customerInput.getAddress())
                .build();
    }
}
