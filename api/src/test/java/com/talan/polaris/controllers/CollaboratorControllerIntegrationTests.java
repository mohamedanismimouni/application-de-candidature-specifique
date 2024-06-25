package com.talan.polaris.controllers;


import com.talan.polaris.dto.*;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.enumerations.CareerPositionStatusEnum;
import com.talan.polaris.enumerations.MentorshipStatusEnum;
import com.talan.polaris.enumerations.ProfileTypeEnum;
import com.talan.polaris.services.CollaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.talan.polaris.Application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })


 class CollaboratorControllerIntegrationTests {

    private MockMvc mockMvc;

    @MockBean
    private CollaboratorService userService;
    @BeforeEach
    public void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = webAppContextSetup(webApplicationContext)

                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document(
                        "{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                      ))
                .build();

    }

    @Test
     void testGetUserById() throws Exception {
        when(this.userService.findById(anyLong())).thenReturn(new CollaboratorEntity());
        this.mockMvc.perform(get("/api/v1/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-user-by-id", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        when(this.userService.findUserByEmail(anyString())).thenReturn(new CollaboratorEntity());
        this.mockMvc.perform(get("/api/v1/users").param("email","john.doe@email.com").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-user-by-email", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestParameters(parameterWithName("email").description("the user's mail"))));
    }

    @Test
    void testFailedGetUserByEmail() throws Exception {
        String email = null;
        when(this.userService.findUserByEmail(anyString())).thenReturn(new CollaboratorEntity());
        this.mockMvc.perform(get("/api/v1/users").param("email",email).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andDo(document("get-user-by-email-", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestParameters(parameterWithName("email").description("the user's mail must be not null"))));
    }

    @Test
    void testGetUsers() throws Exception {
        Collection<CollaboratorEntity> users= new ArrayList<>();
        when(this.userService.findAll()).thenReturn(users);
        this.mockMvc.perform(get("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-users", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                ));
    }

    @Test
     void testGetResourcesByTeamId() throws Exception {
        ProfileTypeEnum profileType= ProfileTypeEnum.MANAGER;
        CollaboratorEntity managedBy= new CollaboratorEntity();
        Collection<CollaboratorEntity> users= new ArrayList<>();
        users.add(managedBy);
        when(this.userService.findResourcesByTeamId(profileType,"1")).thenReturn(users);
        this.mockMvc.perform(get("/api/v1/users")
                .param("profileType",profileType.toString())
                .param("teamId","1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-ressources-by-teamId", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("profileType").description("the resource's profile type"),
                                parameterWithName("teamId").description("the team of the resource")
                                )));
    }

    @Test
    void testWrongTypeGetResourcesByTeamId() throws Exception {
        ProfileTypeEnum profileType= ProfileTypeEnum.MANAGER;
        CollaboratorEntity managedBy= new CollaboratorEntity();
        Collection<CollaboratorEntity> users= new ArrayList<>();
        users.add(managedBy);
        when(this.userService.findResourcesByTeamId(profileType,"1")).thenReturn(users);
        this.mockMvc.perform(get("/api/v1/users/")
                .param("profileType","wrongType")
                .param("teamId","1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("get-ressources-by-teamId-profileType-wrongType", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("profileType").description("the type of this parameter profileType should be ProfileTypeEnum"),
                                parameterWithName("teamId").description("the team Id parameter")
                        )));
    }

    @Test
    @WithMockUser(authorities = "HR_RESPONSIBLE")
    void testGetTeamsAssignmentStatistics() throws Exception {
        int assignedCollaborators=1;
        int unassignedCollaborators=1;
        int assignedManagers=1;
        int unassignedManagers=1;
        when(this.userService.countTeamsAssignmentStatistics()).thenReturn(new TeamsAssignmentStatistics(assignedCollaborators,
                unassignedCollaborators,assignedManagers,unassignedManagers));
        this.mockMvc.perform(get("/api/v1/users/teams-assignment-statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-teams-assignment-statistics", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                ));
    }

    @Test
    void testSendPasswordResetMail() throws Exception {
        this.mockMvc.perform(post("/api/v1/users/password-reset-mail")
                .param("email","john.doe@email.com").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test-send-password-reset-mail", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("email").description("Receive reset password on this mail"))));
    }

    @Test
     void testGetTeamMembers() throws Exception {

        Collection<CollaboratorEntity> users= new ArrayList<>();
        boolean initialized= true;
       String  teamId= "1";
        String profileId="1";
        String careerStepId="1";
        Long supervisorId= 1L;
        CareerPositionStatusEnum careerPositionStatus =  CareerPositionStatusEnum.CURRENT;
        Long mentorId=1L;
        MentorshipStatusEnum mentorshipStatus=MentorshipStatusEnum.ACTIVE;
        LocalDate recruitedBefore= LocalDate.now();
        when(this.userService.findTeamMembers(initialized,teamId,profileId,careerStepId,supervisorId,careerPositionStatus
                ,mentorId,mentorshipStatus,recruitedBefore)).thenReturn(users);
        this.mockMvc.perform(get("/api/v1/users")
                .param("initialized","true")
                .param("teamId","1")
                .param("profileId","1")
                .param("careerStepId","1")
                .param("supervisorId","1")
                .param("careerPositionStatus",careerPositionStatus.toString())
                .param("mentorId","1")
                .param("mentorshipStatus",mentorshipStatus.toString())
                .param("recruitedBefore",recruitedBefore.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test-get-team-members", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                 ,requestParameters(parameterWithName("initialized").description("the user account is initialized or Not"),
                                parameterWithName("teamId").description("the Id of the Team"),
                                parameterWithName("profileId").description("the Id of the profile"),
                                parameterWithName("careerStepId").description("the career step id "),
                                parameterWithName("careerPositionStatus").description("the Id of the Team"),
                                parameterWithName("supervisorId").description("the Id of the Team"),
                                parameterWithName("mentorId").description("the Id of the Team"),
                                parameterWithName("mentorshipStatus").description("the Id of the Team"),
                                parameterWithName("recruitedBefore").description("the Id of the Team")
                                )
                ));
    }

    @Test
    void testNotNullParamsGetTeamMembers() throws Exception {

        Collection<CollaboratorEntity> users= new ArrayList<>();
        boolean initialized= true;
        String  teamId= "1";
        String profileId="1";
        String careerStepId="1";
        Long supervisorId= 1L;
        CareerPositionStatusEnum careerPositionStatus =  CareerPositionStatusEnum.CURRENT;
        Long mentorId= 1L;
        MentorshipStatusEnum mentorshipStatus=MentorshipStatusEnum.ACTIVE;
        LocalDate recruitedBefore= LocalDate.now();
        String teamIdParam= null;
        String profileIdParam= null;
        String careerStepIdParam= null;
        String mentorIdParam= null;
        String supervisorIdParam = null;
        when(this.userService.findTeamMembers(initialized,teamId,profileId,careerStepId,supervisorId,careerPositionStatus
                ,mentorId,mentorshipStatus,recruitedBefore)).thenReturn(users);
        this.mockMvc.perform(get("/api/v1/users")
                .param("initialized","true")
                .param("teamId",teamIdParam)
                .param("profileId",profileIdParam)
                .param("careerStepId",careerStepIdParam)
                .param("supervisorId",supervisorIdParam)
                .param("careerPositionStatus",careerPositionStatus.toString())
                .param("mentorId",mentorIdParam)
                .param("mentorshipStatus",mentorshipStatus.toString())
                .param("recruitedBefore",recruitedBefore.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("test-not-null-params-get-team-members", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("initialized").description("the user account is initialized or Not"),
                                parameterWithName("teamId").description("team id must not be null"),
                                parameterWithName("profileId").description("profile id must not be null"),
                                parameterWithName("careerStepId").description("careerStep id must not be null "),
                                parameterWithName("careerPositionStatus").description("Career position status"),
                                parameterWithName("supervisorId").description("supervisor id must not be null"),
                                parameterWithName("mentorId").description("mentor id must not be null"),
                                parameterWithName("mentorshipStatus").description("mentorship status"),
                                parameterWithName("recruitedBefore").description("the date of recuting")
                        )
                ));
    }

    @Test
    void testTypeParamsGetTeamMembers() throws Exception {
        Collection<CollaboratorEntity> users= new ArrayList<>();
        boolean initialized= true;
        String teamId= "1";
        String profileId="1";
        String careerStepId="1";
        Long supervisorId= 1L;
        CareerPositionStatusEnum careerPositionStatus =  CareerPositionStatusEnum.CURRENT;
        Long mentorId= 1L;
        MentorshipStatusEnum mentorshipStatus=MentorshipStatusEnum.ACTIVE;
        LocalDate recruitedBefore= LocalDate.now();

        when(this.userService.findTeamMembers(initialized,teamId,profileId,careerStepId,supervisorId,careerPositionStatus
                ,mentorId,mentorshipStatus,recruitedBefore)).thenReturn(users);
        this.mockMvc.perform(get("/api/v1/users")
                .param("initialized","true")
                .param("teamId","1")
                .param("profileId","1")
                .param("careerStepId","1")
                .param("supervisorId","1")
                .param("careerPositionStatus","TEST")
                .param("mentorId","1")
                .param("mentorshipStatus","TEST")
                .param("recruitedBefore","TEST")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("test-type-params-get-team-members", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("initialized").description("the user account is initialized or Not"),
                                parameterWithName("teamId").description("the Id of the team"),
                                parameterWithName("profileId").description("the profile id"),
                                parameterWithName("careerStepId").description("the career step id "),
                                parameterWithName("careerPositionStatus").description("the type ofcareerPositionStatus param must be CareerPositionStatusEnum"),
                                parameterWithName("supervisorId").description("the Id of the supervisor"),
                                parameterWithName("mentorId").description("the Id of the mentor"),
                                parameterWithName("mentorshipStatus").description("the type of mentorshipStatus param must be MentorshipStatusEnum"),
                                parameterWithName("recruitedBefore").description("the type of recruitedBefore param must be LocalDate")
                        )
                ));
    }

    @Test
    @WithMockUser(authorities = "HR_RESPONSIBLE")
    void testUpdateUserAccountStatus() throws Exception {
        this.mockMvc.perform(post("/api/v1/users/account-status/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test-update-user-account-status", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @WithMockUser(authorities="HR_RESPONSIBLE")
    void testDeleteUser() throws Exception {
        this.mockMvc.perform(delete("/api/v1/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test-delete-user", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }




















}