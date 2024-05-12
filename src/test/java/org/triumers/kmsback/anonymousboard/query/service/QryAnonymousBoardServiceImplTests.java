package org.triumers.kmsback.anonymousboard.query.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.repository.QryAnonymousBoardMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QryAnonymousBoardServiceImplTests {

    @Mock
    private QryAnonymousBoardMapper qryAnonymousBoardMapper;

    @InjectMocks
    private QryAnonymousBoardServiceImpl qryAnonymousBoardService;

    @Test
    void findAllAnonymousBoard() {
        // given
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(qryAnonymousBoardMapper.findAllAnonymousBoard(pageRequest)).willReturn(anonymousBoardList);
        given(qryAnonymousBoardMapper.countAllAnonymousBoard()).willReturn(1L);

        // when
        Page<QryAnonymousBoardDTO> result = qryAnonymousBoardService.findAllAnonymousBoard(pageRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(anonymousBoardList);
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void searchAnonymousBoard_Title() {
        // given
        String type = "title";
        String keyword = "제목";
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(qryAnonymousBoardMapper.searchAnonymousBoardByTitle(eq(keyword), eq(pageRequest))).willReturn(anonymousBoardList);
        given(qryAnonymousBoardMapper.countAnonymousBoardByTitle(keyword)).willReturn(1L);

        // when
        Page<QryAnonymousBoardDTO> result = qryAnonymousBoardService.searchAnonymousBoard(type, keyword, pageRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(anonymousBoardList);
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void searchAnonymousBoard_Content() {
        // given
        String type = "content";
        String keyword = "내용";
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(qryAnonymousBoardMapper.searchAnonymousBoardByContent(eq(keyword), eq(pageRequest))).willReturn(anonymousBoardList);
        given(qryAnonymousBoardMapper.countAnonymousBoardByContent(keyword)).willReturn(1L);

        // when
        Page<QryAnonymousBoardDTO> result = qryAnonymousBoardService.searchAnonymousBoard(type, keyword, pageRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(anonymousBoardList);
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void searchAnonymousBoard_TitleAndContent() {
        // given
        String type = "titleAndContent";
        String keyword = "검색어";
        List<QryAnonymousBoardDTO> anonymousBoardList = new ArrayList<>();
        anonymousBoardList.add(new QryAnonymousBoardDTO());
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(qryAnonymousBoardMapper.searchAnonymousBoardByTitleAndContent(eq(keyword), eq(pageRequest))).willReturn(anonymousBoardList);
        given(qryAnonymousBoardMapper.countAnonymousBoardByTitleAndContent(keyword)).willReturn(1L);

        // when
        Page<QryAnonymousBoardDTO> result = qryAnonymousBoardService.searchAnonymousBoard(type, keyword, pageRequest);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(anonymousBoardList);
        assertThat(result.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void searchAnonymousBoard_InvalidType() {
        // given
        String type = "invalid";
        String keyword = "검색어";
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when, then
        assertThatThrownBy(() -> qryAnonymousBoardService.searchAnonymousBoard(type, keyword, pageRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid search type: " + type);
    }

    @Test
    void findAnonymousBoardById() {
        // given
        int id = 1;
        QryAnonymousBoardDTO anonymousBoard = new QryAnonymousBoardDTO();
        given(qryAnonymousBoardMapper.findAnonymousBoardById(id)).willReturn(anonymousBoard);

        // when
        QryAnonymousBoardDTO result = qryAnonymousBoardService.findAnonymousBoardById(id);

        // then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(anonymousBoard);
    }

    @Test
    void findAnonymousBoardById_NotFound() {
        // given
        int id = 1;
        given(qryAnonymousBoardMapper.findAnonymousBoardById(id)).willReturn(null);

        // when, then
        assertThatThrownBy(() -> qryAnonymousBoardService.findAnonymousBoardById(id))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Anonymous board not found with id: " + id);
    }
}