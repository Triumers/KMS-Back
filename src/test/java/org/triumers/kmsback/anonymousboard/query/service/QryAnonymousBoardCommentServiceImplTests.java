package org.triumers.kmsback.anonymousboard.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardCommentService;
import org.triumers.kmsback.anonymousboard.command.Application.service.CmdAnonymousBoardService;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardRepository;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryAnonymousBoardCommentServiceImplTests {

    @Autowired
    private QryAnonymousBoardCommentService qryAnonymousBoardCommentService;

    @Autowired
    private CmdAnonymousBoardService cmdAnonymousBoardService;

    @Autowired
    private CmdAnonymousBoardCommentService cmdAnonymousBoardCommentService;

    @Autowired
    private CmdAnonymousBoardRepository cmdAnonymousBoardRepository;

    @Autowired
    private CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository;

    private int anonymousBoardId;

    @BeforeEach
    void setUp() {

        cmdAnonymousBoardCommentRepository.deleteAll();
        cmdAnonymousBoardRepository.deleteAll();

        CmdAnonymousBoardDTO cmdAnonymousBoardDTO = new CmdAnonymousBoardDTO();
        cmdAnonymousBoardDTO.setTitle("Test Board");
        cmdAnonymousBoardDTO.setContent("Test Board Content");

        CmdAnonymousBoardDTO savedAnonymousBoardDTO = cmdAnonymousBoardService.saveAnonymousBoard(cmdAnonymousBoardDTO);

        CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO1 = new CmdAnonymousBoardCommentDTO();
        cmdAnonymousBoardCommentDTO1.setNickname("Test Nickname 1");
        cmdAnonymousBoardCommentDTO1.setContent("Test Comment 1");
        cmdAnonymousBoardCommentDTO1.setAnonymousBoard(savedAnonymousBoardDTO.toEntity());

        CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO2 = new CmdAnonymousBoardCommentDTO();
        cmdAnonymousBoardCommentDTO2.setNickname("Test Nickname 2");
        cmdAnonymousBoardCommentDTO2.setContent("Test Comment 2");
        cmdAnonymousBoardCommentDTO2.setAnonymousBoard(savedAnonymousBoardDTO.toEntity());

        cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO1);
        cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO2);

        anonymousBoardId = savedAnonymousBoardDTO.getId();
    }

    @DisplayName("익명게시판 게시글 댓글 전부 조회 성공 여부 테스트")
    @Test
    void findAllAnonymousBoardComment_ValidAnonymousBoardId_ReturnsComments() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<QryAnonymousBoardCommentDTO> commentPage = qryAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, pageRequest);

        assertNotNull(commentPage);
        assertEquals(2, commentPage.getTotalElements());
        assertEquals("Test Comment 2", commentPage.getContent().get(0).getContent());
        assertEquals("Test Comment 1", commentPage.getContent().get(1).getContent());
    }

    @DisplayName("익명게시판 유효하지 않은 게시글의 댓글 조회할 시 예외 발생 여부 테스트")
    @Test
    void findAllAnonymousBoardComment_InvalidAnonymousBoardId_ThrowsNoSuchElementException() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        int invalidAnonymousBoardId = 999;

        assertThrows(NoSuchElementException.class, () -> {
            qryAnonymousBoardCommentService.findAllAnonymousBoardComment(invalidAnonymousBoardId, pageRequest);
        });
    }

    @DisplayName("익명게시판 게시글 댓글 개수 조회 성공 여부 테스트")
    @Test
    void countAnonymousBoardComment_ValidAnonymousBoardId_ReturnsCommentCount() {
        long commentCount = qryAnonymousBoardCommentService.countAnonymousBoardComment(anonymousBoardId);

        assertEquals(2, commentCount);
    }

    @DisplayName("익명게시판 유효하지 않은 게시글의 댓글 개수 조회할 시 예외 발생 여부 테스트")
    @Test
    void countAnonymousBoardComment_InvalidAnonymousBoardId_ReturnsZero() {
        int invalidAnonymousBoardId = 999;

        long commentCount = qryAnonymousBoardCommentService.countAnonymousBoardComment(invalidAnonymousBoardId);

        assertEquals(0, commentCount);
    }
}