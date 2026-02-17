package com.algaworks.algashop.ordering.infrastructure.utility.modelmapper;

import com.algaworks.algashop.ordering.domain.model.common.FullName;
import com.algaworks.algashop.ordering.domain.model.customer.valueobjects.BirthDate;
import io.hypersistence.tsid.TSID;
import lombok.experimental.UtilityClass;
import org.modelmapper.Converter;

import java.time.LocalDate;

@UtilityClass
public class ModelMapperCustomConverters {

    public static final Converter<FullName, String> fullNameToFirstNameConverter =
            context -> {
                FullName fullName = context.getSource();
                if (fullName == null) {
                    return null;
                }
                return fullName.firstName();
            };

    public static final Converter<FullName, String> fullNameToLastNameConverter =
            context -> {
                FullName fullName = context.getSource();
                if (fullName == null) {
                    return null;
                }
                return fullName.lastName();
            };

    public static final Converter<BirthDate, LocalDate> bithDateToLocalDateConverter =
            context -> {
                BirthDate birthDate = context.getSource();
                if (birthDate == null) {
                    return null;
                }
                return birthDate.value();
            };

    public static final Converter<Long, String> tsidLongToStringConverter =
            context -> {
                Long tsidAsLong = context.getSource();
                if (tsidAsLong== null) {
                    return null;
                }
                return new TSID(tsidAsLong).toString();
            };
}
