package com.talan.polaris.controllers;

import com.talan.polaris.Application;
import com.talan.polaris.entities.CollaboratorEntity;
import com.talan.polaris.services.CollaboratorAPIByblosService;
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

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest(classes = Application.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class CollabControllerIntegrationTests {
    private MockMvc mockMvc;

    @MockBean
    private CollaboratorAPIByblosService collaboratorAPIByblosService;
    @MockBean
    private CollaboratorService collaboratorService;

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
    @WithMockUser(authorities = "HR_RESPONSIBLE")
    void testGetCollabs() throws Exception {
        Collection<CollaboratorEntity> collabEntities= new ArrayList<>();
        when(this.collaboratorService.findAll()).thenReturn(collabEntities);
        this.mockMvc.perform(get("/api/v1/collab")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("test-get-collab", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                ));
    }
}
