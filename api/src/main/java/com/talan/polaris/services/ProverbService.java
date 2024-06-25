package com.talan.polaris.services;
import com.talan.polaris.entities.ProverbEntity;
import java.util.Collection;

/**
 * ProverbService.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
public interface ProverbService {
     Collection<ProverbEntity> getProverbs();
     ProverbEntity addProverb(ProverbEntity proverb);
     void deleteProverb(Long idproverb);
     ProverbEntity getPublishedProverb();
     void publishedProverb ();
     Boolean existProverOfDay();


}
