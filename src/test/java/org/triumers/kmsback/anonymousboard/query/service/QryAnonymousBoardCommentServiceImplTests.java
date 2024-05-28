package org.triumers.kmsback.anonymousboard.query.service;

import org.junit.jupiter.api.BeforeEach;
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

    private int anonymousBoardId;

    @BeforeEach
    void setUp() {
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

    @Test
    void findAllAnonymousBoardComment_ValidAnonymousBoardId_ReturnsComments() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<QryAnonymousBoardCommentDTO> commentPage = qryAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, pageRequest);

        assertNotNull(commentPage);
        assertEquals(2, commentPage.getTotalElements());
        assertEquals("Test Comment 2", commentPage.getContent().get(0).getContent());
        assertEquals("Test Comment 1", commentPage.getContent().get(1).getContent());
    }

    @Test
    void findAllAnonymousBoardComment_InvalidAnonymousBoardId_ThrowsNoSuchElementException() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        int invalidAnonymousBoardId = 999;

        assertThrows(NoSuchElementException.class, () -> {
            qryAnonymousBoardCommentService.findAllAnonymousBoardComment(invalidAnonymousBoardId, pageRequest);
        });
    }
}