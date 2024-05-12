package org.triumers.kmsback.anonymousboard.query.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.service.QryAnonymousBoardService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = QryAnonymousBoardController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class QryAnonymousBoardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QryAnonymousBoardService qryAnonymousBoardService;

    @Test
    void searchAnonymousBoard_AllList() throws Exception {
        // given
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        Page<QryAnonymousBoardDTO> page = new PageImpl<>(anonymousBoardList);
        given(qryAnonymousBoardService.findAllAnonymousBoard(any(PageRequest.class))).willReturn(page);

        // when, then
        mockMvc.perform(get("/anonymous-board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void searchAnonymousBoard_ByTitle() throws Exception {
        // given
        String type = "title";
        String keyword = "제목";
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        Page<QryAnonymousBoardDTO> page = new PageImpl<>(anonymousBoardList);
        given(qryAnonymousBoardService.searchAnonymousBoard(eq(type), eq(keyword), any(PageRequest.class))).willReturn(page);

        // when, then
        mockMvc.perform(get("/anonymous-board")
                        .param("type", type)
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void searchAnonymousBoard_ByContent() throws Exception {
        // given
        String type = "content";
        String keyword = "내용";
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        Page<QryAnonymousBoardDTO> page = new PageImpl<>(anonymousBoardList);
        given(qryAnonymousBoardService.searchAnonymousBoard(eq(type), eq(keyword), any(PageRequest.class))).willReturn(page);

        // when, then
        mockMvc.perform(get("/anonymous-board")
                        .param("type", type)
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void searchAnonymousBoard_ByTitleAndContent() throws Exception {
        // given
        String type = "titleAndContent";
        String keyword = "검색어";
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        Page<QryAnonymousBoardDTO> page = new PageImpl<>(anonymousBoardList);
        given(qryAnonymousBoardService.searchAnonymousBoard(eq(type), eq(keyword), any(PageRequest.class))).willReturn(page);

        // when, then
        mockMvc.perform(get("/anonymous-board")
                        .param("type", type)
                        .param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void searchAnonymousBoard_InvalidType() throws Exception {
        // given
        String type = "invalid";
        String keyword = "검색어";
        given(qryAnonymousBoardService.searchAnonymousBoard(eq(type), eq(keyword), any(PageRequest.class)))
                .willThrow(new IllegalArgumentException("Invalid search type: " + type));

        // when, then
        mockMvc.perform(get("/anonymous-board")
                        .param("type", type)
                        .param("keyword", keyword))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid search type: " + type));
    }

    @Test
    void getAnonymousBoardById() throws Exception {
        // given
        int id = 1;
        QryAnonymousBoardDTO anonymousBoard = new QryAnonymousBoardDTO();
        anonymousBoard.setId(id);
        given(qryAnonymousBoardService.findAnonymousBoardById(id)).willReturn(anonymousBoard);

        // when, then
        mockMvc.perform(get("/anonymous-board/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getAnonymousBoardById_NotFound() throws Exception {
        // given
        int id = 1;
        given(qryAnonymousBoardService.findAnonymousBoardById(id)).willThrow(new NoSuchElementException("Anonymous board not found with id: " + id));

        // when, then
        mockMvc.perform(get("/anonymous-board/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Anonymous board not found with id: " + id));
    }
}