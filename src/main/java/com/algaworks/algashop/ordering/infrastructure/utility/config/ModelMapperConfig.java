package com.algaworks.algashop.ordering.infrastructure.utility.config;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.order.output.OrderDetailOutput;
import com.algaworks.algashop.ordering.application.model.order.output.OrderItemDetailOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.entity.OrderPersistenceEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.algaworks.algashop.ordering.infrastructure.utility.modelmapper.ModelMapperCustomConverters.*;

@Configuration
public class ModelMapperConfig {

    @Bean
    public Mapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        configurations(modelMapper);
        return modelMapper::map;
    }

    private void configurations(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setSourceNamingConvention(NamingConventions.NONE)
                .setDestinationNamingConvention(NamingConventions.NONE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(Customer.class, CustomerOutput.class)
                .addMappings(mapping ->
                        mapping.using(fullNameToFirstNameConverter).map(
                                Customer::fullName,
                                CustomerOutput::setFirstName))
                .addMappings(mapping ->
                        mapping.using(fullNameToLastNameConverter).map(
                                Customer::fullName,
                                CustomerOutput::setLastName))
                .addMappings(mapping ->
                        mapping.using(bithDateToLocalDateConverter).map(
                                Customer::birthDate,
                                CustomerOutput::setBirthDate)
                );

        modelMapper.createTypeMap(OrderPersistenceEntity.class, OrderDetailOutput.class)
                .addMappings(mapping ->
                        mapping.using(tsidLongToStringConverter).map(
                                OrderPersistenceEntity::getId,
                                OrderDetailOutput::setId)
                );

        modelMapper.createTypeMap(OrderItemPersistenceEntity.class, OrderItemDetailOutput.class)
                .addMappings(mapping ->
                        mapping.using(tsidLongToStringConverter).map(
                                OrderItemPersistenceEntity::getId,
                                OrderItemDetailOutput::setId)
                ).addMappings(mapping ->
                        mapping.using(tsidLongToStringConverter).map(
                                OrderItemPersistenceEntity::getOrderId,
                                OrderItemDetailOutput::setOrderId)
                );
    }
}
