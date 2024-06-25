package com.talan.polaris.services.impl;

import com.talan.polaris.repositories.RequestTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;


/**
 * Unit tests class for methods implemented in {@link RequestTypeServiceImplTests}.
 *
 * @author Chaima maiza
 * @since 1.0.0
 */
class RequestTypeServiceImplTests {
    private static final String DEFAULT_SORT_FIELD_NAME = "createdAt";


    @Mock
    private RequestTypeRepository requestTypeRepository;

    @InjectMocks
    private RequestTypeServiceImpl requestTypeServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }



    @Test
     void  findRequestType__whenCalled_thenCallRequestTypeRepository() {

        // given + when
        this.requestTypeServiceImpl.findRequestType();
        // then
        verify(this.requestTypeRepository, only()).findAll(Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD_NAME));
    }

}
