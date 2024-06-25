package com.talan.polaris.controllers;

import com.talan.polaris.Application;
import com.talan.polaris.dto.TeamDTO;
import com.talan.polaris.entities.TeamEntity;
import com.talan.polaris.mapper.TeamMapper;
import com.talan.polaris.services.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import javax.json.JsonPatch;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class TeamControllerIntegrationTests {


    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

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
     void testGetTeamByManagerId() throws Exception {
        when(this.teamService.findTeamByManagerId(anyLong())).thenReturn(new TeamEntity());
        this.mockMvc.perform(get("/api/v1/teams")
                .param("managedBy","1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-team-by-manager-id-isOK", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("managedBy").description("the id ohf the manager"))));
    }

    @Test
    void testNotNullParamGetTeamByManagerId() throws Exception {
        String managedBy = null;
        when(this.teamService.findTeamByManagerId(anyLong())).thenReturn(new TeamEntity());
        this.mockMvc.perform(get("/api/v1/teams")
                .param("managedBy",managedBy)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("test-not-null-param-get-team-by-manager-id", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ,requestParameters(parameterWithName("managedBy").description("the manager id must be not null"))));
    }

    @Test
    @WithMockUser(authorities = "HR_RESPONSIBLE")
    void testGetTeams() throws Exception {
        Collection<TeamEntity> teams= new ArrayList<>();
        when(this.teamService.findAll()).thenReturn(teams);
        this.mockMvc.perform(get("/api/v1/teams")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test-get-teams", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        ));
    }



}
