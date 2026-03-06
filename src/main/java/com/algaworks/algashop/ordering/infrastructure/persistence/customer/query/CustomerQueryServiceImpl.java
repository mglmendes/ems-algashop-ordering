package com.algaworks.algashop.ordering.infrastructure.persistence.customer.query;

import com.algaworks.algashop.ordering.application.model.customer.filter.CustomerFilter;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerOutput;
import com.algaworks.algashop.ordering.application.model.customer.output.CustomerSummaryOutput;
import com.algaworks.algashop.ordering.application.model.customer.query.CustomerQueryService;
import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.customer.jpql.CustomerJPQLQueries;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.entity.CustomerPersistenceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final EntityManager entityManager;

    @Override
    public CustomerOutput findById(UUID customerId) {
        Objects.requireNonNull(customerId);
        try {
            TypedQuery<CustomerOutput> query = entityManager.createQuery(CustomerJPQLQueries.findByIdAsOutputJPQL,
                    CustomerOutput.class);
            query.setParameter("id", customerId);
            return query.getSingleResult();
        } catch (NoResultException e) {
           throw new CustomerNotFoundException();
        }
    }

    @Override
    public Page<CustomerSummaryOutput> filter(CustomerFilter filter) {
        long totalQueryResults = countTotalQueryResults(filter);

        if (totalQueryResults == 0L){
            PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
            return new PageImpl<>(new ArrayList<>(), pageRequest, totalQueryResults);
        }
        return filterQuery(filter, totalQueryResults);
    }

    private Page<CustomerSummaryOutput> filterQuery(CustomerFilter filter, long totalQueryResults) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerSummaryOutput> criteriaQuery = builder.createQuery(CustomerSummaryOutput.class);
        Root<CustomerPersistenceEntity> root = criteriaQuery.from(CustomerPersistenceEntity.class);

        criteriaQuery.select(
                builder.construct(CustomerSummaryOutput.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("document"),
                        root.get("phone"),
                        root.get("birthDate"),
                        root.get("loyaltyPoints"),
                        root.get("registeredAt"),
                        root.get("archivedAt"),
                        root.get("promotionNotificationsAllowed"),
                        root.get("archived"))
        );

        Predicate[] predicates = toPredicates(builder, root, filter);
        Order sortOrder = toSortOrder(builder, root, filter);

        criteriaQuery.where(predicates);

        if (sortOrder != null) {
            criteriaQuery.orderBy(sortOrder);
        }

        TypedQuery<CustomerSummaryOutput> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(filter.getSize() * filter.getPage());
        typedQuery.setMaxResults(filter.getSize());

        PageRequest pageRequest = PageRequest.of(filter.getPage(), filter.getSize());
        return new PageImpl<>(typedQuery.getResultList(), pageRequest, totalQueryResults);

    }

    private Order toSortOrder(CriteriaBuilder builder, Root<CustomerPersistenceEntity> root, CustomerFilter filter) {
        if (filter.getSortDirectionOrDefault() == Sort.Direction.ASC) {
            return builder.asc(
                    root.get(filter.getSortByPropertyOrDefault().getPropertyName())
            );
        }

        if (filter.getSortDirectionOrDefault() == Sort.Direction.DESC) {
            return builder.desc(
                    root.get(filter.getSortByPropertyOrDefault().getPropertyName())
            );
        }
        return null;
    }

    private long countTotalQueryResults(CustomerFilter filter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<CustomerPersistenceEntity> root = criteriaQuery.from(CustomerPersistenceEntity.class);
        Expression<Long> count = builder.count(root);
        Predicate[] predicates = toPredicates(builder, root, filter);

        criteriaQuery.select(count);
        criteriaQuery.where(predicates);

        TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getSingleResult();
    }

    private Predicate[] toPredicates(CriteriaBuilder builder,
                                     Root<CustomerPersistenceEntity> root, CustomerFilter filter) {
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get("firstName")), "%" + filter.getFirstName().toLowerCase() + "%"));
        }

        if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
            predicates.add(builder.like(builder.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
        }

        return predicates.toArray(new Predicate[]{});
    }
}
