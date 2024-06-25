package com.talan.polaris.services.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.domain.Sort;

import com.talan.polaris.entities.GenericEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.repositories.GenericRepository;
import com.talan.polaris.services.GenericService;

/**
 * An implementation of {@link GenericService}, containing the implementation of
 * common methods between every service managing an entity that extends
 * {@link GenericEntity}.
 * <p>
 * This class is not meant to be instanciated by the bean container, it is meant
 * to be extended by other services' implementations only.
 * 
 * @param <E> the entity type.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public class GenericServiceImpl<E extends GenericEntity> implements GenericService<E> {

    private static final String DEFAULT_SORT_FIELD_NAME = "createdAt";

    private final GenericRepository<E> repository;

    public GenericServiceImpl(GenericRepository<E> repository) {
        this.repository = repository;
    }

    @Override
    public E findById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Override
    public Collection<E> findAll() {
        return this.findAll(Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD_NAME));
    }

    @Override
    public Collection<E> findAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    @Override
    public E create(E entity) {
        entity.setId(UUID.randomUUID().toString());
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public E update(E entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public void deleteById(String id) {
        this.repository.delete(this.findById(id));
    }

    @Override
    public void deleteInBatch(Collection<E> entities) {
        this.repository.deleteInBatch(entities);
    }

}
