package com.talan.polaris.services;
import com.talan.polaris.entities.UsefulLinkEntity;
import java.util.Collection;

/**
 *
 * definitions related to {@link UsefulLinkEntity}.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
public interface UsefulLinkService  {

    public Collection<UsefulLinkEntity> findAllUsefulLink();


}
