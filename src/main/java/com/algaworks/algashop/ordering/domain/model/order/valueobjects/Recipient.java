package com.algaworks.algashop.ordering.domain.model.order.valueobjects;

import com.algaworks.algashop.ordering.domain.model.common.Document;
import com.algaworks.algashop.ordering.domain.model.common.FullName;
import com.algaworks.algashop.ordering.domain.model.common.Phone;
import lombok.Builder;

import java.util.Objects;

@Builder
public record Recipient(
        FullName fullName,
        Document document,
        Phone phone
) {

    public Recipient {
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(document);
        Objects.requireNonNull(phone);
    }
}
