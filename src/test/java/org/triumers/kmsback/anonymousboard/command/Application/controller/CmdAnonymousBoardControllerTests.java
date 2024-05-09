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
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CmdAnonymousBoardController.class)
public class CmdAnonymousBoardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CmdAnonymousBoardService cmdAnonymousBoardService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    // 익명 게시판 목록 조회 테스트
    @Test
    void getAnonymousBoardList_shouldReturnAnonymousBoardDTOList() throws Exception {
        List<CmdAnonymousBoardDTO> anonymousBoardDTOList = Arrays.asList(
                new CmdAnonymousBoardDTO(),
                new CmdAnonymousBoardDTO()
        );
        Page<CmdAnonymousBoardDTO> anonymousBoardDTOPage = new PageImpl<>(anonymousBoardDTOList);

        when(cmdAnonymousBoardService.findAllAnonymousBoard(any(Pageable.class))).thenReturn(anonymousBoardDTOPage);

        mockMvc.perform(get("/anonymous-board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));

        verify(cmdAnonymousBoardService, times(1)).findAllAnonymousBoard(any(Pageable.class));
    }

    // 익명 게시판 검색 테스트
    @Test
    void searchAnonymousBoard_shouldReturnAnonymousBoardDTOList() throws Exception {
        List<CmdAnonymousBoardDTO> anonymousBoardDTOList = Arrays.asList(
                new CmdAnonymousBoardDTO(),
                new CmdAnonymousBoardDTO()
        );
        Page<CmdAnonymousBoardDTO> anonymousBoardDTOPage = new PageImpl<>(anonymousBoardDTOList);

        when(cmdAnonymousBoardService.searchAnonymousBoardByTitle(eq("keyword"), any(Pageable.class))).thenReturn(anonymousBoardDTOPage);

        mockMvc.perform(get("/anonymous-board/search")
                        .param("type", "title")
                        .param("keyword", "keyword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));

        verify(cmdAnonymousBoardService, times(1)).searchAnonymousBoardByTitle(eq("keyword"), any(Pageable.class));
    }

    // 익명 게시글 작성 테스트
    @Test
    void createAnonymousBoard_shouldReturnCreatedAnonymousBoardDTO() throws Exception {
        CmdAnonymousBoardDTO anonymousBoardDTO = new CmdAnonymousBoardDTO();
        anonymousBoardDTO.setTitle("Title");
        anonymousBoardDTO.setContent("Content");

        when(cmdAnonymousBoardService.saveAnonymousBoard(any(CmdAnonymousBoardDTO.class))).thenReturn(anonymousBoardDTO);

        mockMvc.perform(post("/anonymous-board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(anonymousBoardDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));

        verify(cmdAnonymousBoardService, times(1)).saveAnonymousBoard(any(CmdAnonymousBoardDTO.class));
    }

    // 익명 게시글 삭제 테스트
    @Test
    void deleteAnonymousBoard_shouldDeleteAnonymousBoard() throws Exception {
        int id = 1;

        mockMvc.perform(delete("/anonymous-board/{id}", id))
                .andExpect(status().isNoContent());

        verify(cmdAnonymousBoardService, times(1)).deleteAnonymousBoard(eq(id));
    }
}