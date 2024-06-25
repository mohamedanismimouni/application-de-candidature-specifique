package com.talan.polaris.services.impl;

import com.talan.polaris.entities.ProverbEntity;
import com.talan.polaris.repositories.ProverbRepository;
import com.talan.polaris.services.CollaboratorService;
import com.talan.polaris.services.ProverbService;
import com.talan.polaris.services.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.talan.polaris.constants.CommonConstants.PROVERB_COMPONENT_LABEL;
import static com.talan.polaris.constants.ConfigurationConstants.LOAD_COLLAB_CRON;
import static com.talan.polaris.constants.ConfigurationConstants.PROVERB_CRON;

/**
 * An implementation of {@link ProverbService}, containing business methods
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
@Service
public class ProverbServiceImpl
        implements ProverbService {

    private final ProverbRepository proverbRepository;
    private final ScoreService scoreService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProverbServiceImpl.class);


    @Autowired
    public ProverbServiceImpl(
            ProverbRepository proverbRepository,
            ScoreService scoreService,
            CollaboratorService collaboratorService) {
        this.proverbRepository = proverbRepository;
        this.scoreService = scoreService;

    }

    @Override
    public Collection<ProverbEntity> getProverbs() {
    return proverbRepository.getProverbs();
    }

    @Override
    public ProverbEntity addProverb(ProverbEntity proverb) {
        LOGGER.info("add proverb");
        proverb.setPublished(true);
        ProverbEntity newProverb = this.proverbRepository.save(proverb);
        this.scoreService.increment(proverb.getCreator().getId(), PROVERB_COMPONENT_LABEL);

        return newProverb;
    }

    @Override
    public void deleteProverb(Long idProverb) {
        LOGGER.info("delete proverb");
        ProverbEntity proverb = proverbRepository.findById(idProverb).get();
        if(proverb!=null) {
            this.scoreService.decrement(proverb.getCreator().getId(), PROVERB_COMPONENT_LABEL);
            this.proverbRepository.deleteById(idProverb);
        }
    }

    @Override
    public ProverbEntity getPublishedProverb() {
        List<ProverbEntity> proverbEntities = this.proverbRepository.getPublishedProverb();
        if (!proverbEntities.isEmpty())
        {
            return  proverbEntities.get(0);
        }
            return null;
    }

    @Override
    public Boolean existProverOfDay() {
            List<ProverbEntity> proverbEntities = this.proverbRepository.getPublishedProverb();
            return  proverbEntities.isEmpty();
    }

    @Override
    @Scheduled(cron = "${" + PROVERB_CRON + "}", zone = "Europe/Paris")
    public void publishedProverb ()
    {
        List<ProverbEntity> proverbEntities = this.proverbRepository.getPublishedProverb();
        ProverbEntity proverbEntity= proverbEntities.get(0);
        proverbEntity.setPublished(false);
        this.proverbRepository.save(proverbEntity);
    }

}
