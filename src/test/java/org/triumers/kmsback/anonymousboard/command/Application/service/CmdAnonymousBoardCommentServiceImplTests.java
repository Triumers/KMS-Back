package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;
import org.triumers.kmsback.anonymousboard.command.domain.repository.CmdAnonymousBoardCommentRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    // 댓글 조회(페이징 처리까지)
    @Test
    void findAllAnonymousBoardComment_shouldReturnPageOfCmdAnonymousBoardCommentDTO() {
        int anonymousBoardId = 1;
        List<CmdAnonymousBoardComment> cmdAnonymousBoardCommentList = Arrays.asList(cmdAnonymousBoardComment1, cmdAnonymousBoardComment2);
        Page<CmdAnonymousBoardComment> cmdAnonymousBoardCommentPage = new PageImpl<>(cmdAnonymousBoardCommentList);

        when(cmdAnonymousBoardCommentRepository.findByAnonymousBoardId(eq(anonymousBoardId), any(Pageable.class))).thenReturn(cmdAnonymousBoardCommentPage);

        Page<CmdAnonymousBoardCommentDTO> result = cmdAnonymousBoardCommentService.findAllAnonymousBoardComment(anonymousBoardId, Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(cmdAnonymousBoardCommentRepository, times(1)).findByAnonymousBoardId(eq(anonymousBoardId), any(Pageable.class));
    }
}