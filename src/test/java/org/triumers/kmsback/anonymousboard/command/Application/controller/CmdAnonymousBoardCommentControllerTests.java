package org.triumers.kmsback.anonymousboard.command.Application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardCommentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CmdAnonymousBoardCommentController.class)
class CmdAnonymousBoardCommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CmdAnonymousBoardCommentService cmdAnonymousBoardCommentService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // 댓글 목록 조회 테스트
    @Test
    void getAnonymousBoardCommentList_shouldReturnAnonymousBoardCommentDTOList() throws Exception {
        int anonymousBoardId = 1;
        List<CmdAnonymousBoardCommentDTO> anonymousBoardCommentDTOList = Arrays.asList(
                new CmdAnonymousBoardCommentDTO(),
                new CmdAnonymousBoardCommentDTO()
        );
        Page<CmdAnonymousBoardCommentDTO> anonymousBoardCommentDTOPage = new PageImpl<>(anonymousBoardCommentDTOList);

        when(cmdAnonymousBoardCommentService.findAllAnonymousBoardComment(eq(anonymousBoardId), any(Pageable.class))).thenReturn(anonymousBoardCommentDTOPage);

        mockMvc.perform(get("/anonymous-board/{anonymousBoardId}/comments", anonymousBoardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));

        verify(cmdAnonymousBoardCommentService, times(1)).findAllAnonymousBoardComment(eq(anonymousBoardId), any(Pageable.class));
    }

}