package com.talan.polaris.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.talan.polaris.entities.ChoiceEntity;
import com.talan.polaris.entities.SelectedChoiceEntity;
import com.talan.polaris.repositories.SelectedChoiceRepository;
import com.talan.polaris.services.SelectedChoiceService;

/**
 * An implementation of {@link SelectedChoiceService}, containing business methods
 * implementations specific to {@link SelectedChoiceEntity}, and may override some of
 * the common methods' implementations inherited from
 * {@link GenericServiceImpl}.
 * 
 * @author Nader Debbabi
 * @since 1.0.0
 */
@Service
public class SelectedChoiceServiceImpl
        extends GenericServiceImpl<SelectedChoiceEntity>
        implements SelectedChoiceService {

    private final SelectedChoiceRepository selectedChoiceRepository;

    @Autowired
    public SelectedChoiceServiceImpl(SelectedChoiceRepository repository) {
        super(repository);
        this.selectedChoiceRepository = repository;
    }

    @Override
    public Collection<ChoiceEntity> findChoicesByResponseId(String responseId) {
        return this.selectedChoiceRepository.findChoicesByResponseId(responseId);
    }

}
