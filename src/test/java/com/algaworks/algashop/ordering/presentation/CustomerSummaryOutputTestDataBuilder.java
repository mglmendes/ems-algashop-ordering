package com.algaworks.algashop.ordering.presentation;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerSummaryOutput;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class CustomerSummaryOutputTestDataBuilder {

 public static CustomerSummaryOutput.CustomerSummaryOutputBuilder existing() {
  return CustomerSummaryOutput.builder()
    .id(UUID.randomUUID())
    .registeredAt(OffsetDateTime.now())
    .phone("1191234564")
    .email("johndoe@email.com")
    .firstName("John")
    .lastName("Doe")
    .birthDate(LocalDate.of(1991, 7, 5))
    .document("12345")
    .promotionNotificationsAllowed(false)
          .archived(false)
    .loyaltyPoints(0);
 }

 public static CustomerSummaryOutput.CustomerSummaryOutputBuilder existingAlt1() {
   return CustomerSummaryOutput.builder()
     .id(UUID.randomUUID())
     .registeredAt(OffsetDateTime.now())
     .phone("119123456")
     .email("scott1977@email.com")
     .firstName("Scott")
     .lastName("Stacey")
     .birthDate(LocalDate.of(1977, 1, 5))
     .document("98745")
     .promotionNotificationsAllowed(true)
           .archived(true)
     .loyaltyPoints(10);
 }

}