package com.algaworks.algashop.ordering.domain.model.generic;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID> extends Repository<T, ID> {
    void remove(T t);
    void remove(ID id);
}
