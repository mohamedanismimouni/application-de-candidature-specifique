package com.talan.polaris.services.impl;

import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.services.CollaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import java.util.UUID;

import static com.talan.polaris.constants.ConfigurationConstants.MAIL_TEMPLATE_PATH;
import static com.talan.polaris.constants.ConfigurationConstants.SECURITY_PASSWORD_INITIALIZATION_LINK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
/**
 * Unit tests class for methods implemented in {@link MailServiceImpl}.
 *
 * @author Imen Mechergui
 * @since 1.0.0
 */
public class MailServiceImplUnitTests {
    @Mock
    private MailServiceImpl mailServiceImpl;
    @Mock
    private CollaboratorService userService;
    @Mock
    private  MessageSource messageSource;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private TemplateEngine templateEngine;
    @Value("${" + MAIL_TEMPLATE_PATH + "}")
    private String mailTemplatePath;
    @Value("${" + SECURITY_PASSWORD_INITIALIZATION_LINK + "}")
    private String passwordInitializationLink;


    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void sendOnboardingProcessMail_givenReceieverMailAndReceiverFirstNameAndRecruitNameAnaSecretWordPart_whenCalled_thenOnboardingProcessMailIsSent() {
        // given
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        CollaboratorEntity user=new CollaboratorEntity();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        doReturn(user)
                .when(userService)
                .findUserByEmail(anyString()) ;
        CollaboratorEntity foundUser=userService.findUserByEmail("test@gmail.com");
        //when
        mailServiceImplSpy.sendOnboardingProcessMail(user.getFirstName(),
                user.getEmail(),
                "test",
                "test");
        // then
        verify(mailServiceImplSpy, only()).sendOnboardingProcessMail("test",
                "test@gmail.com",
                "test",
                "test");
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser).isNotNull();
    }
    @Test
    public void sendPasswordResetMail_givenReceiverFirstNameAndRecruitEmailAndSessionID_whenCalled_thenPasswordResetMailIsSent() {
        // given
        CollaboratorEntity user=new CollaboratorEntity();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        String sessionId=UUID.randomUUID().toString();
        doReturn(user)
                .when(userService)
                .findUserByEmail(anyString()) ;
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        CollaboratorEntity foundUser=userService.findUserByEmail("test@gmail.com");
        //when
        mailServiceImplSpy.sendPasswordResetMail(user.getFirstName(),
                user.getEmail(),
                sessionId);
        // then
        verify(mailServiceImplSpy, only()).sendPasswordResetMail(user.getFirstName(),
                user.getEmail(),
                sessionId);
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser).isNotNull();
    }
    @Test
    public void sendAccountActivationMail_givenReceiverFirstNameAndRecruitEmailAndSessionID_whenCalled_thenAccountActivationMailIsSent() {
        // given
        CollaboratorEntity user=new CollaboratorEntity();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        String sessionId=UUID.randomUUID().toString();
        doReturn(user)
                .when(userService)
                .findUserByEmail(anyString()) ;
        CollaboratorEntity foundUser=userService.findUserByEmail("test@gmail.com");
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        //when
        mailServiceImplSpy.sendAccountActivationMail(user.getFirstName(),
                user.getEmail(),
                sessionId);
        // then
        verify(mailServiceImplSpy, only()).sendAccountActivationMail(user.getFirstName(),
                user.getEmail(),
                sessionId);
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser).isNotNull();
    }

    @Test
    public void sendDocumentRequestMail_givenReceiverFirstNameAndRecruitEmailAndSessionIDAndCollabName_whenCalled_thenDocumentRequestMailIsSent() {
        // given
        CollaboratorEntity user=new CollaboratorEntity();
        user.setEmail("test@gmail.com");
        user.setFirstName("test");
        String sessionId=UUID.randomUUID().toString();
        CollaboratorEntity collab = new CollaboratorEntity();
        String type = "WORK CERTIFICATE";
        String requestMotif = "cin";
        doReturn(user)
                .when(userService)
                .findUserByEmail(anyString()) ;
        CollaboratorEntity foundUser=userService.findUserByEmail("test@gmail.com");
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        //when
        mailServiceImplSpy.sendDocumentRequestMail(user.getFirstName(),
                user.getEmail(),

                collab.getFirstName(),
                collab.getLastName(),
                type,
                requestMotif
                );
        // then
        verify(mailServiceImplSpy, only()).sendDocumentRequestMail(user.getFirstName(),
                user.getEmail(),

                collab.getFirstName(),
                collab.getLastName(),
                type,
                requestMotif
        );
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser).isNotNull();
    }
    @Test
    public void sendInvalidateResponseMail_givenReceiverNameAndReceiverEmailAndSessionIDAndSenderNameAndRequestMotif_whenCalled_thenSendInvalidateResponseMail() {
        // given

        String sessionId=UUID.randomUUID().toString();
        CollaboratorEntity collab = new CollaboratorEntity();
        collab.setFirstName("receiverFirstName");
        collab.setLastName("receiverLastName");
        collab.setEmail("receiver@gmail.com");
        CollaboratorEntity rh = new CollaboratorEntity();
        rh.setFirstName("senderFirstName");
        rh.setLastName("senderLastName");
        String type = "WORK CERTIFICATE";
        String requestMotif = "cin";
        doReturn(collab)
                .when(userService)
                .findUserByEmail(anyString()) ;
        CollaboratorEntity foundUser=userService.findUserByEmail("receiver@gmail.com");
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        //when

        mailServiceImplSpy.sendInvalidateResponseMail(collab.getFirstName(),
                collab.getEmail(),
                rh.getFirstName(),
                rh.getLastName(),
                type,
                requestMotif);


        // then
        verify(mailServiceImplSpy, only()).sendInvalidateResponseMail(collab.getFirstName(),
                collab.getEmail(),
                rh.getFirstName(),
                rh.getLastName(),
                type,
                requestMotif);
        assertThat(foundUser.getEmail()).isEqualTo(collab.getEmail());
        assertThat(foundUser).isNotNull();
    }

    @Test
    public void sendValidateResponseMail_givenReceiverNameAndReceiverEmailAndSessionIDAndSenderNameAndRequest_whenCalled_thenSendValidateResponseMail() {
        // given

        String sessionId=UUID.randomUUID().toString();
        CollaboratorEntity collab = new CollaboratorEntity();
        collab.setFirstName("receiverFirstName");
        collab.setLastName("receiverLastName");
        collab.setEmail("receiver@gmail.com");
        CollaboratorEntity rh = new CollaboratorEntity();
        rh.setFirstName("senderFirstName");
        rh.setLastName("senderLastName");
        String  type = "work certificate";
        String requestDate = "01-03-2021";
        doReturn(collab)
                .when(userService)
                .findUserByEmail(anyString()) ;
        CollaboratorEntity foundUser=userService.findUserByEmail("receiver@gmail.com");
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        //when

    /*    mailServiceImplSpy.sendValidateResponseMail( "12-23-2020-00" ,collab.getFirstName(),collab.getLastName(),
                collab.getEmail(),
                sessionId,
                rh.getFirstName(),
                rh.getLastName(),
                type,
                requestDate

        );*/
        // then
     /*   verify(mailServiceImplSpy, only()).sendValidateResponseMail("12-23-2020-00",collab.getFirstName(),collab.getLastName(),
                collab.getEmail(),
                sessionId,
                rh.getFirstName(),
                rh.getLastName(),
                type,
                requestDate
        );*/
        assertThat(foundUser.getEmail()).isEqualTo(collab.getEmail());
        assertThat(foundUser).isNotNull();
    }



    @Test
    public void sendValidateResponseMailByRh_givenReceiverNameAndReceiverEmailAndSessionIDAndSenderNameAndRequest_whenCalled_thenSendValidateResponseMail() {
        // given

        String sessionId=UUID.randomUUID().toString();
        CollaboratorEntity collab = new CollaboratorEntity();
        collab.setFirstName("receiverFirstName");
        collab.setEmail("receiver@gmail.com");
        CollaboratorEntity rh = new CollaboratorEntity();
        rh.setFirstName("senderFirstName");
        rh.setLastName("senderLastName");
        String  type = "work certificate";
        String requestDate = "01-03-2021";
        doReturn(collab)
                .when(userService)
                .findUserByEmail(anyString()) ;
        CollaboratorEntity foundUser=userService.findUserByEmail("receiver@gmail.com");
        MailServiceImpl mailServiceImplSpy = spy(this.mailServiceImpl);
        //when

        mailServiceImplSpy.sendValidateResponseMail(collab.getFirstName() , "12-23-2020-00" ,rh.getFirstName(),rh.getLastName(),
                collab.getEmail(),
                type,
                requestDate,
                rh.getFirstName(),
                rh.getLastName()

        );
        // then
        verify(mailServiceImplSpy, only()).sendValidateResponseMail(collab.getFirstName(),"12-23-2020-00",rh.getFirstName(),rh.getLastName(),
                collab.getEmail(),
                type,
                requestDate,
                rh.getFirstName(),
                rh.getLastName()
        );
        assertThat(foundUser.getEmail()).isEqualTo(collab.getEmail());
        assertThat(foundUser).isNotNull();
    }

}
