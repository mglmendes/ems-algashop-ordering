package com.algaworks.algashop.ordering.domain.model.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T t);
}
