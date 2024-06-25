package com.talan.polaris.services;

import java.util.Collection;

import org.springframework.data.domain.Sort;

import com.talan.polaris.entities.GenericEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;

/**
 * A base service interface, containing the defenition of common methods between
 * every service managing an entity that extends the {@link GenericEntity}. Thus,
 * those services must extend this interface.
 * 
 * @param <E> the entity type.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface GenericService<E extends GenericEntity> {

    /**
     * Finds an entity by its {@code id}.
     * 
     * @param id of the entity, must not be {@code null} otherwise the underlying
     * {@link com.talan.polaris.repositories.GenericRepository#findById(Object)}
     * will throw an {@link IllegalArgumentException}.
     * 
     * @return the entity associated with the given id, never {@code null}.
     * 
     * @throws ResourceNotFoundException if no entity was found for the given id.
     */
    public E findById(String id);

    /**
     * Finds and returns all entities.
     * 
     * @return all entities.
     */
    public Collection<E> findAll();

    /**
     * Finds and returns all entities, sorted by the given sort options defined by
     * the {@link Sort} parameter.
     * 
     * @param sort options, used to sort the found entities.
     * 
     * @return all entities sorted by the given sort options.
     */
    public Collection<E> findAll(Sort sort);

    /**
     * Sets the id and saves the given entity.
     * 
     * @param entity to be created.
     * 
     * @return the created entity.
     */
    public E create(E entity);

    /**
     * Updates the given entity.
     * 
     * @param entity to be updated.
     * 
     * @return the updated entity.
     */
    public E update(E entity);

    /**
     * Deletes an entity by its id.
     * 
     * @param id of the entity, must not be {@code null} otherwise the underlying
     * {@link com.talan.polaris.repositories.GenericRepository#findById(Object)}
     * will throw an {@link IllegalArgumentException}.
     * 
     * @throws ResourceNotFoundException if no entity was found for the given id.
     */
    public void deleteById(String id);

    /**
     * A wrapper arround
     * {@link com.talan.polaris.repositories.GenericRepository#deleteInBatch(Iterable)}.
     * 
     * @param entities to be deleted in batch.
     */
    public void deleteInBatch(Collection<E> entities);

}
