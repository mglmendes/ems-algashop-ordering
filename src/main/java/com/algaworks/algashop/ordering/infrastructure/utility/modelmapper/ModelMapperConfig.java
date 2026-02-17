package com.algaworks.algashop.ordering.infrastructure.utility.modelmapper;

import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.common.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.BirthDate;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ModelMapperConfig {

    private static final Converter<FullName, String> fullNameToFirstNameConverter =
            context -> {
        FullName fullName = context.getSource();
        if (fullName == null) {
            return null;
        }
        return fullName.firstName();
    };

    private static final Converter<FullName, String> fullNameToLastNameConverter =
            context -> {
                FullName fullName = context.getSource();
                if (fullName == null) {
                    return null;
                }
                return fullName.lastName();
    };

    private static final Converter<BirthDate, LocalDate> bithDateToLocalDateConverter =
            context -> {
                BirthDate birthDate = context.getSource();
                if (birthDate == null) {
                    return null;
                }
                return birthDate.value();
    };

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
    }
}
