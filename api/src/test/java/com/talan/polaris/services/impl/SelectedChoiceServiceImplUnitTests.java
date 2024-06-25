package com.talan.polaris.services.impl;

import com.talan.polaris.repositories.SelectedChoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link SelectedChoiceServiceImpl}.
 * 
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class SelectedChoiceServiceImplUnitTests {

    @Mock
    private SelectedChoiceRepository selectedChoiceRepository;
    @InjectMocks
    private SelectedChoiceServiceImpl selectedChoiceServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void findChoicesByResponseId_givenResponseId_whenCalled_thenCallSelectedChoiceRepository() {
        // given + when
        this.selectedChoiceServiceImpl.findChoicesByResponseId(anyString());
        // then
        verify(this.selectedChoiceRepository, only()).findChoicesByResponseId(anyString());
    }
}
