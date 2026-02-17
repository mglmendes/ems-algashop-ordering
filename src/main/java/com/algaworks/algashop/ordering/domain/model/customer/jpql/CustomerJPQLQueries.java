package com.algaworks.algashop.ordering.domain.model.customer.jpql;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerJPQLQueries {

    public static final String findByIdAsOutputJPQL = """
            SELECT new com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput(
                c.id,
                c.firstName,
                c.lastName,
                c.phone,
                c.email,
                c.document,
                c.birthDate,
                c.promotionNotificationsAllowed,
                c.loyaltyPoints,
                c.registeredAt,
                c.archivedAt,
                c.archived,
                new com.algaworks.algashop.ordering.application.model.common.AddressData(
                    c.address.street,
                    c.address.number,
                    c.address.complement,
                    c.address.neighborhood,
                    c.address.city,
                    c.address.state,
                    c.address.zipCode
                )
            )
            FROM CustomerPersistenceEntity c
            WHERE c.id = :id""";
}
