package com.interview.challenge.pokerplanning.controller;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessionControllerTest {

    private static final String SESSION_BY_ID_URL = "/sessions/f7176674-213c-4d27-af02-198b98e42387";
    private static final String VOTES_URL = "/votes";
    private static final String USER_STORIES_URL = "/user-stories";
    public static final String MEMBERS_URL = "/members";

    @Autowired
    private MockMvc mvc;

    @Test
    @Sql({"/sessions.sql"})
    public void shouldReturnAllSessions() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/sessions")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("title") && content.contains("test"));
    }

    @Test
    @Sql({"/sessions.sql"})
    public void shouldReturnSessionById() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(SESSION_BY_ID_URL)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("title") && content.contains("test"));
    }

    @Test
    public void shouldCreateSession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post("/sessions")
                .content("{\"title\": \"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("title") && content.contains("test"));
    }

    @Test
    @Sql({"/sessions.sql"})
    public void shouldDeleteSession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .delete(SESSION_BY_ID_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("title") && content.contains("test"));
    }

    @Test
    @Sql({"/sessions.sql"})
    public void shouldCreateUserStoryBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(SESSION_BY_ID_URL + USER_STORIES_URL)
                .content("{\"description\": \"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("description") && content.contains("test"));
    }

    @Test
    @Sql({"/sessions.sql", "/user-stories.sql"})
    public void shouldReturnAllUserStoriesBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(SESSION_BY_ID_URL + USER_STORIES_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("description") && content.contains("test"));
    }

    @Test
    @Sql({"/sessions.sql", "/user-stories.sql"})
    public void shouldDeleteUserStoryBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .delete(SESSION_BY_ID_URL + USER_STORIES_URL + "/f7176674-213c-4d27-af02-198b98e42388")
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        mvc.perform(MockMvcRequestBuilders
                .get(SESSION_BY_ID_URL + USER_STORIES_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("description") && content.contains("test"));
    }

    @Test
    @Sql({"/sessions.sql", "/user-stories-voting-status.sql"})
    public void shouldThrowIllegalArgumentExceptionWhenDeleteUserStoryBySession() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete(SESSION_BY_ID_URL + USER_STORIES_URL + "/f7176674-213c-4d27-af02-198b98e42385")
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
            .andExpect(result -> assertEquals(
                "User story with status VOTING can not be deleted",
                result.getResolvedException().getMessage()));
    }

    @Test
    @Sql({"/sessions.sql", "/user-stories.sql"})
    public void shouldUpdateUserStoryBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .put(SESSION_BY_ID_URL + USER_STORIES_URL + "/f7176674-213c-4d27-af02-198b98e42388")
                .content("""
                        {
                            "id": "7d0260f9-0aeb-4eaa-b08d-ea9d19c7576c",
                            "description": "test",
                            "status": "VOTING"
                        }
                    """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("status") && content.contains("VOTING"));
    }

    @Test
    @Sql({"/sessions.sql"})
    public void shouldCreateMemberBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(SESSION_BY_ID_URL + MEMBERS_URL)
                .content("{\"name\": \"testName\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("name") && content.contains("testName"));
    }

    @Test
    @Sql({"/sessions.sql", "/members.sql"})
    public void shouldReturnAllMembersBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(SESSION_BY_ID_URL + MEMBERS_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("name") && content.contains("testName"));
    }

    @Test
    @Sql({"/sessions.sql", "/members.sql"})
    public void shouldThrowNoSuchElementExceptionWhenThereIsNoSession() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/sessions/f7176674-213c-4d27-af02-198b98e42381" + MEMBERS_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
            .andExpect(result -> assertEquals(
                "Session with id f7176674-213c-4d27-af02-198b98e42381 does not exist",
                result.getResolvedException().getMessage()));
    }

    @Test
    @Sql({"/sessions.sql", "/members.sql"})
    public void shouldDeleteMemberBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .delete(SESSION_BY_ID_URL + MEMBERS_URL + "/f7176674-213c-4d27-af02-198b98e42381")
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        mvc.perform(MockMvcRequestBuilders
                .get(SESSION_BY_ID_URL + MEMBERS_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("name") && content.contains("testName"));
    }

    @Test
    @Sql({"/sessions.sql", "/members.sql", "/user-stories.sql", "/votes.sql"})
    public void shouldReturnAllVotesBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get(SESSION_BY_ID_URL + VOTES_URL)
                .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("count"));
    }

    @Test
    @Sql({"/sessions.sql", "/members.sql", "/user-stories.sql"})
    public void shouldCreateVoteBySession() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(SESSION_BY_ID_URL + VOTES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "member": {
                            "id": "f7176674-213c-4d27-af02-198b98e42381"
                        },
                        "userStory": {
                            "id": "f7176674-213c-4d27-af02-198b98e42388"
                            },
                        "count": "1"
                    }
                    """)
                .accept(MediaType.ALL))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("id"));
    }
}