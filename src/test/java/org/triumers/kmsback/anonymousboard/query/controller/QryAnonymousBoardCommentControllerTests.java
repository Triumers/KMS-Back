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
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.query.service.QryAnonymousBoardCommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = QryAnonymousBoardCommentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class QryAnonymousBoardCommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QryAnonymousBoardCommentService qryAnonymousBoardCommentService;

    @Test
    void getAnonymousBoardCommentList() throws Exception {
        // given
        int anonymousBoardId = 1;
        List<QryAnonymousBoardCommentDTO> anonymousBoardCommentList = new ArrayList<>();
        anonymousBoardCommentList.add(new QryAnonymousBoardCommentDTO());
        Page<QryAnonymousBoardCommentDTO> page = new PageImpl<>(anonymousBoardCommentList);
        given(qryAnonymousBoardCommentService.findAllAnonymousBoardComment(eq(anonymousBoardId), any(PageRequest.class))).willReturn(page);

        // when, then
        mockMvc.perform(get("/anonymous-board/{anonymousBoardId}/comments", anonymousBoardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void getAnonymousBoardCommentList_NotFound() throws Exception {
        // given
        int anonymousBoardId = 1;
        given(qryAnonymousBoardCommentService.findAllAnonymousBoardComment(eq(anonymousBoardId), any(PageRequest.class)))
                .willThrow(new NoSuchElementException("Anonymous board comments not found for anonymousBoardId: " + anonymousBoardId));

        // when, then
        mockMvc.perform(get("/anonymous-board/{anonymousBoardId}/comments", anonymousBoardId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Anonymous board comments not found for anonymousBoardId: " + anonymousBoardId));
    }
}