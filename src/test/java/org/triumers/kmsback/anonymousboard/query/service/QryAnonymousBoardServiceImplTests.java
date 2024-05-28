package org.triumers.kmsback.anonymousboard.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QryAnonymousBoardServiceImplTests {

    @Autowired
    private QryAnonymousBoardService qryAnonymousBoardService;

    @Autowired
    private CmdAnonymousBoardService cmdAnonymousBoardService;

    private static final int PAGE_SIZE = 10;

    @BeforeEach
    void setUp() {
        CmdAnonymousBoardDTO cmdAnonymousBoardDTO1 = new CmdAnonymousBoardDTO(0, "익명", "제목1", "내용1", null, null);
        CmdAnonymousBoardDTO cmdAnonymousBoardDTO2 = new CmdAnonymousBoardDTO(0, "익명", "제목2", "내용2", null, null);
        CmdAnonymousBoardDTO cmdAnonymousBoardDTO3 = new CmdAnonymousBoardDTO(0, "익명", "title1", "content1", null, null);
        CmdAnonymousBoardDTO cmdAnonymousBoardDTO4 = new CmdAnonymousBoardDTO(0, "익명", "title2", "content2", null, null);
        CmdAnonymousBoardDTO cmdAnonymousBoardDTO5 = new CmdAnonymousBoardDTO(0, "익명", "title3", "content3", null, null);
        cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO1);
        cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO2);
        cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO3);
        cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO4);
        cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO5);
    }

    @Test
    void findAllAnonymousBoard() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.findAllAnonymousBoard(pageable);

        assertThat(page.getTotalElements()).isEqualTo(5);
        List<QryAnonymousBoardDTO> anonymousBoardList = page.getContent();
        assertThat(anonymousBoardList).hasSize(5);
    }

    @Test
    void searchAnonymousBoardByTitle() {
        String keyword = "제목";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.searchAnonymousBoard(keyword, "title", pageable);

        assertThat(page.getTotalElements()).isEqualTo(2);
        List<QryAnonymousBoardDTO> anonymousBoardList = page.getContent();
        assertThat(anonymousBoardList).hasSize(2);
    }

    @Test
    void searchAnonymousBoardByContent() {
        String keyword = "내용";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.searchAnonymousBoard(keyword, "content", pageable);

        assertThat(page.getTotalElements()).isEqualTo(2);
        List<QryAnonymousBoardDTO> anonymousBoardList = page.getContent();
        assertThat(anonymousBoardList).hasSize(2);
    }

    @Test
    void searchAnonymousBoardByTitleAndContent() {
        String keyword = "내용";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.searchAnonymousBoard(keyword, "titleandcontent", pageable);

        assertThat(page.getTotalElements()).isEqualTo(2);
        List<QryAnonymousBoardDTO> anonymousBoardList = page.getContent();
        assertThat(anonymousBoardList).hasSize(2);
    }

    @Test
    void findAnonymousBoardById() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.findAllAnonymousBoard(pageable);
        List<QryAnonymousBoardDTO> anonymousBoardList = page.getContent();
        QryAnonymousBoardDTO qryAnonymousBoardDTO = anonymousBoardList.get(0);

        QryAnonymousBoardDTO foundAnonymousBoard = qryAnonymousBoardService.findAnonymousBoardById(qryAnonymousBoardDTO.getId());

        assertThat(foundAnonymousBoard.getTitle()).isEqualTo(qryAnonymousBoardDTO.getTitle());
        assertThat(foundAnonymousBoard.getContent()).isEqualTo(qryAnonymousBoardDTO.getContent());
        assertThat(foundAnonymousBoard.getCreatedDate()).isNotNull();
        assertThat(foundAnonymousBoard.getMacAddress()).isNotNull();
    }

    @Test
    void searchAnonymousBoardByTitleCount() {
        String keyword = "title";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.searchAnonymousBoard(keyword, "title", pageable);

        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    void searchAnonymousBoardByContentCount() {
        String keyword = "content";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.searchAnonymousBoard(keyword, "content", pageable);

        assertThat(page.getTotalElements()).isEqualTo(3);
    }

    @Test
    void searchAnonymousBoardByTitleAndContentCount() {
        String keyword = "title";
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        Page<QryAnonymousBoardDTO> page = qryAnonymousBoardService.searchAnonymousBoard(keyword, "titleandcontent", pageable);

        assertThat(page.getTotalElements()).isEqualTo(3);
    }
}