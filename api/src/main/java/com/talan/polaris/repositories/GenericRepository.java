package com.talan.polaris.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.talan.polaris.entities.GenericEntity;

/**
 * A specific extension of {@link JpaRepository}, and a base repository for all
 * other repositories managing an entity that extends the {@link GenericEntity}.
 * <p>
 * Specifies the entity type as a generic parameter that extends 
 * {@link GenericEntity}, and the id type as a {@link String}.
 * 
 * @param <E> the entity type.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
public interface GenericRepository<E extends GenericEntity> extends JpaRepository<E, String> {

}
