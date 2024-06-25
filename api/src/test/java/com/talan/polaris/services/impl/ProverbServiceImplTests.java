package com.talan.polaris.services.impl;

import com.talan.polaris.entities.*;
import com.talan.polaris.repositories.ProverbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Unit tests class for methods implemented in {@link ProverbServiceImplTests}.
 *
 * @author Imen Mechergui
 * @since 2.0.0
 */
class ProverbServiceImplTests {


    @Mock
    private ProverbRepository proverbRepository;

    @InjectMocks
    private ProverbServiceImpl proverbServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }



    @Test
     void  getProverbs_whenCalled_thenCallProverbRepository() {
        // given + when
        this.proverbServiceImpl.getProverbs();
        // then
        verify(this.proverbRepository, only()).getProverbs();
    }

    @Test
    void  getPublishedProverb_whenCalled_thenCallProverbRepository() {
        // given + when
        this.proverbServiceImpl.getPublishedProverb();
        // then
        verify(this.proverbRepository, only()).getPublishedProverb();
    }

    @Test
    void  existProverOfDay_whenCalled_thenCallProverbRepository() {
        // given + when
        this.proverbServiceImpl.existProverOfDay();
        // then
        verify(this.proverbRepository, only()).getPublishedProverb();
    }


    @Test
    void  deleteProverb_whenCalled_thenCallProverbRepository() {
        // given + when
        this.proverbServiceImpl.deleteProverb(anyLong());
        // then
        verify(this.proverbRepository, only()).deleteById(anyLong());
    }


    @Test
    void addProverb_givenProfileEntity_whenCalled_thenProverbIsCreated() {
        //Proverb Entity
        ProverbEntity proverb =new ProverbEntity();
        proverb.setCreatedAt(Instant.now());
        proverb.setUpdatedAt(Instant.now());
        proverb.setText(("La joie est en tout ; il faut savoir l'extraire"));
        proverb.setAuthor("Confucius\n");
        proverb.setCreator(new CollaboratorEntity());
        //spy proverbServiceImplSpy
        ProverbServiceImpl proverbServiceImplSpy = spy(this.proverbServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(proverbServiceImplSpy)
                .addProverb(any(ProverbEntity.class));
        // when
        ProverbEntity createdProverb = proverbServiceImplSpy.addProverb(proverb);
        // then
        assertThat(createdProverb).isNotNull();
        assertThat(createdProverb.getText()).isEqualTo(proverb.getText());
        assertThat(createdProverb.getAuthor()).isEqualTo(proverb.getAuthor());

    }

}
