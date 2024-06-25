package com.talan.polaris.services.impl;

import com.talan.polaris.entities.GenericEntity;
import com.talan.polaris.exceptions.ResourceNotFoundException;
import com.talan.polaris.repositories.GenericRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests class for methods implemented in {@link GenericServiceImpl}.
 *
 * @author Nader Debbabi
 * @since 1.0.0
 */
class GenericServiceImplUnitTests {

    @Mock
    private GenericRepository<GenericEntity> repository;

    @InjectMocks
    private GenericServiceImpl<GenericEntity> genericServiceImpl;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void findById_givenAnId_whenEntityIsNotFound_thenThrowsResourceNotFoundException() {

        // given
        given(this.repository.findById(anyString()))
                .willReturn(Optional.ofNullable(null));

        // when + then
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> this.genericServiceImpl.findById(anyString()));

    }

    @Test
    void findById_givenAnId_whenEntityIsFound_thenReturnsTheEntity() {

        // given
        String id = UUID.randomUUID().toString();
        GenericEntity entity = new GenericEntity(id, Instant.now(), Instant.now());
        given(this.repository.findById(id)).willReturn(Optional.of(entity));

        // when
        GenericEntity foundEntity = this.genericServiceImpl.findById(id);

        // then
        assertThat(foundEntity).isNotNull().isEqualToComparingFieldByField(entity);

    }

    @Test
    void findAll_givenNoArguments_thenFindAllWithSortObject() {

        // given
        GenericServiceImpl<GenericEntity> genericServiceImplSpy = spy(this.genericServiceImpl);
        doReturn(Collections.emptyList()).when(genericServiceImplSpy).findAll(any(Sort.class));

        // when
        genericServiceImplSpy.findAll();

        // then
        verify(genericServiceImplSpy, times(1)).findAll(any(Sort.class));

    }

    @Test
    void findAll_givenASortObject_thenCallGenericRepository() {

        // given
        Sort sort = Sort.unsorted();
        given(this.repository.findAll(any(Sort.class))).willReturn(Collections.emptyList());

        // when
        this.genericServiceImpl.findAll(sort);

        // then
        verify(this.repository, only()).findAll(sort);

    }

    @Test
    void create_givenAnEntity_whenEverythingIsValid_thenReturnsEntity() {

        // given
        given(this.repository.saveAndFlush(any(GenericEntity.class)))
                .willAnswer((i) -> {
                    GenericEntity persistedEntity = i.getArgument(0);
                    persistedEntity.setCreatedAt(Instant.now());
                    persistedEntity.setUpdatedAt(Instant.now());
                    return persistedEntity;
                });

        // when
        GenericEntity createdEntity = this.genericServiceImpl.create(new GenericEntity());

        // then
        assertThat(createdEntity.getId()).isNotBlank();
        assertThat(createdEntity.getCreatedAt()).isNotNull();
        assertThat(createdEntity.getUpdatedAt()).isNotNull();

    }

    @Test
    void update_givenAnEntity_whenEverythingIsValid_thenEntityIsUpdated() {

        // given
        GenericEntity entity = new GenericEntity(
                UUID.randomUUID().toString(),
                Instant.parse("2007-12-03T10:15:30.00Z"),
                Instant.parse("2007-12-03T10:15:30.00Z"));

        given(this.repository.saveAndFlush(any(GenericEntity.class)))
                .willAnswer((i) -> {
                    GenericEntity entityToBeUpdated = i.getArgument(0);
                    return new GenericEntity(
                            entityToBeUpdated.getId(),
                            entityToBeUpdated.getCreatedAt(),
                            Instant.now());
                });

        // when
        GenericEntity updatedEntity = this.genericServiceImpl.update(entity);

        // then
        assertThat(updatedEntity.getId()).isEqualTo(entity.getId());
        assertThat(updatedEntity.getCreatedAt()).isEqualTo(entity.getCreatedAt());
        assertThat(updatedEntity.getUpdatedAt()).isNotEqualTo(entity.getUpdatedAt());

    }

    @Test
    void deleteById_givenAnId_whenEntityIsFound_thenEntityIsDeleted() {

        // given
        GenericServiceImpl<GenericEntity> genericServiceImplSpy = spy(this.genericServiceImpl);
        doReturn(new GenericEntity())
                .when(genericServiceImplSpy)
                .findById(anyString());

        // when
        genericServiceImplSpy.deleteById(anyString());

        // then
        verify(this.repository, times(1)).delete(any(GenericEntity.class));

    }

    @Test
    void deleteInBatch_givenCollectionOfEntities_whenEntitiesAreDeleted_thenCollectionIsDeleted() {

        // given
        doNothing().when(this.repository).deleteInBatch(anyCollection());

        // when
        this.genericServiceImpl.deleteInBatch(anyCollection());

        // then
        verify(this.repository, only()).deleteInBatch(anyCollection());

    }

}
