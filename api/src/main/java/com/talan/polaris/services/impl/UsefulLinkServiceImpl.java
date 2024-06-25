package com.talan.polaris.services.impl;

import com.talan.polaris.entities.UsefulLinkEntity;
import com.talan.polaris.repositories.UsefulLinkRepository;
import com.talan.polaris.services.UsefulLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * An implementation of {@link UsefulLinkService}, containing business methods
 * implementations specific to {@link UsefulLinkEntity}, and may override some
 * of the common methods' implementations inherited from *
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
@Service
public class UsefulLinkServiceImpl implements UsefulLinkService {

    private final UsefulLinkRepository usefulLinkRepository;

    @Autowired
    public UsefulLinkServiceImpl(
            UsefulLinkRepository usefulLinkRepository) {
            this.usefulLinkRepository = usefulLinkRepository;
    }

    /**
     * get all useful links
     * @return
     */
    @Override
    public Collection<UsefulLinkEntity> findAllUsefulLink() {
        return usefulLinkRepository.findAll();
    }
}
