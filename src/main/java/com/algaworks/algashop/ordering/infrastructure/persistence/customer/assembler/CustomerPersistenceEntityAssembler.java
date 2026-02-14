package com.algaworks.algashop.ordering.infrastructure.persistence.customer.assembler;

import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.commons.Address;
import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceEntityAssembler {

    public CustomerPersistenceEntity fromDomain(Customer customer) {
        return merge(new CustomerPersistenceEntity(), customer);
    }

    public CustomerPersistenceEntity merge(CustomerPersistenceEntity persistenceEntity, Customer customer) {
        persistenceEntity.setId(customer.id().value());
        persistenceEntity.setVersion(customer.version());
        persistenceEntity.setFirstName(customer.fullName().firstName());
        persistenceEntity.setLastName(customer.fullName().lastName());
        persistenceEntity.setBirthDate(customer.birthDate() != null ? customer.birthDate().value() : null);
        persistenceEntity.setEmail(customer.email().value());
        persistenceEntity.setPhone(customer.phone().value());
        persistenceEntity.setDocument(customer.document().value());
        persistenceEntity.setPromotionNotificationsAllowed(customer.promotionNotificationsAllowed());
        persistenceEntity.setArchived(customer.isArchived());
        persistenceEntity.setRegisteredAt(customer.registeredAt());
        persistenceEntity.setArchivedAt(customer.archivedAt());
        persistenceEntity.setLoyaltyPoints(customer.loyaltyPoints().value());
        persistenceEntity.setAddress(toAddressEmbeddable(customer.address()));
        return persistenceEntity;
    }

    private AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) {
            return null;
        }
        return AddressEmbeddable.builder()
                .city(address.city())
                .state(address.state())
                .number(address.number())
                .street(address.street())
                .complement(address.complement())
                .neighborhood(address.neighborhood())
                .zipCode(address.zipCode().value())
                .build();
    }
}
