package com.algaworks.algashop.ordering.application.model.order.filter;

import com.algaworks.algashop.ordering.application.utility.SortablePageFilter;
import lombok.*;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilter extends SortablePageFilter<OrderFilter.SortType> {

    private String status;
    private String orderId;
    private UUID customerId;
    private OffsetDateTime placedAtFrom;
    private OffsetDateTime placedAtTo;

    private BigDecimal totalAmountFrom;
    private BigDecimal totalAmountTo;

    public OrderFilter(int size, int page) {
        super(size, page);
    }

    @Override
    public SortType getSortByPropertyOrDefault() {
        return getSortByProperty() == null ? SortType.PLACED_AT : getSortByProperty();
    }

    @Override
    public Sort.Direction getSortDirectionOrDefault() {
        return getSortDirection() == null ? Sort.Direction.DESC : getSortDirection();
    }

    @RequiredArgsConstructor
    @Getter
    public enum SortType {
        PLACED_AT("placed_at"),
        PAID_AT("paid_at"),
        CANCELED_AT("canceled_at"),
        READY_AT("ready_at"),
        STATUS("status");

        private final String propertyName;
    }
}
