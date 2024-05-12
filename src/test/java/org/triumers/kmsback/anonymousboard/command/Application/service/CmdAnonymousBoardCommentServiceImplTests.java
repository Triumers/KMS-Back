package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CmdAnonymousBoardCommentServiceImplTests {

    @Mock
    private CmdAnonymousBoardCommentRepository cmdAnonymousBoardCommentRepository;

    @InjectMocks
    private CmdAnonymousBoardCommentServiceImpl cmdAnonymousBoardCommentService;

    private CmdAnonymousBoardComment cmdAnonymousBoardComment1;
    private CmdAnonymousBoardComment cmdAnonymousBoardComment2;

    @BeforeEach
    void setUp() {
        cmdAnonymousBoardComment1 = new CmdAnonymousBoardComment();
        cmdAnonymousBoardComment1.setId(1);
        cmdAnonymousBoardComment1.setNickname("익명1");
        cmdAnonymousBoardComment1.setContent("댓글 내용 1");
        cmdAnonymousBoardComment1.setCreatedDate(LocalDateTime.now());
        cmdAnonymousBoardComment1.setMacAddress("MAC1");
        cmdAnonymousBoardComment1.setAnonymousBoardId(1);

        cmdAnonymousBoardComment2 = new CmdAnonymousBoardComment();
        cmdAnonymousBoardComment2.setId(2);
        cmdAnonymousBoardComment2.setNickname("익명2");
        cmdAnonymousBoardComment2.setContent("댓글 내용 2");
        cmdAnonymousBoardComment2.setCreatedDate(LocalDateTime.now());
        cmdAnonymousBoardComment2.setMacAddress("MAC2");
        cmdAnonymousBoardComment2.setAnonymousBoardId(1);
    }

    // 댓글 작성
    @Test
    void saveAnonymousBoardComment_shouldSaveCmdAnonymousBoardComment() throws Exception {
        CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO = new CmdAnonymousBoardCommentDTO();
        cmdAnonymousBoardCommentDTO.setNickname("익명");
        cmdAnonymousBoardCommentDTO.setContent("댓글 내용");
        cmdAnonymousBoardCommentDTO.setAnonymousBoardId(1);

        when(cmdAnonymousBoardCommentRepository.save(any(CmdAnonymousBoardComment.class))).thenAnswer(invocation -> {
            CmdAnonymousBoardComment savedCmdAnonymousBoardComment = invocation.getArgument(0);
            assertNotNull(savedCmdAnonymousBoardComment.getMacAddress());
            return savedCmdAnonymousBoardComment;
        });

        CmdAnonymousBoardCommentDTO result = cmdAnonymousBoardCommentService.saveAnonymousBoardComment(cmdAnonymousBoardCommentDTO);

        assertNotNull(result);
        assertNotNull(result.getMacAddress());
        assertEquals(1, result.getAnonymousBoardId());
        verify(cmdAnonymousBoardCommentRepository, times(1)).save(any(CmdAnonymousBoardComment.class));
    }

    // 댓글 삭제
    @Test
    void deleteAnonymousBoardComment_shouldDeleteCmdAnonymousBoardComment() {
        int id = 1;

        cmdAnonymousBoardCommentService.deleteAnonymousBoardComment(id);

        verify(cmdAnonymousBoardCommentRepository, times(1)).deleteById(eq(id));
    }
}