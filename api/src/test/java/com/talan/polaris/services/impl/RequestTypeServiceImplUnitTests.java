package com.talan.polaris.services.impl;
import com.talan.polaris.entities.RequestTypeEntity;
import com.talan.polaris.repositories.RequestTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link RequestTypeServiceImpl}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class RequestTypeServiceImplUnitTests {
    @Mock
    private RequestTypeRepository requestTypeRepository;
    @InjectMocks
    private RequestTypeServiceImpl requestTypeServiceImpl;
    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void updateRequestType_givenRequestType_whenCalled_thenRequestTypeIsUpdated() {
       RequestTypeEntity requestType=new  RequestTypeEntity();
       String id = UUID.randomUUID().toString();
        requestType.setIdEDM((long) 15555);
        //spy DocumentRequestServiceImpl
        RequestTypeServiceImpl resuestTypeServiceImplSpy = spy(this.requestTypeServiceImpl);
        //get result from request
        doAnswer((invocation) -> invocation.getArgument(0))
                .when(resuestTypeServiceImplSpy)
                .update(any(RequestTypeEntity.class));
        // when
       RequestTypeEntity createdType= resuestTypeServiceImplSpy.update(requestType);
       assertThat(createdType).isNotNull();

    }
}
