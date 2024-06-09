package org.triumers.kmsback.comment.command.Application.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.CmdComment;
import org.triumers.kmsback.comment.command.Domain.repository.CommentRepository;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CmdCommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("댓글 추가 테스트")
    @Test
    public void testAddComment() throws NotLoginException {
        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setContent("Test Comment");
        dto.setAuthorId(1L);
        dto.setPostId(1L);

        CmdComment savedComment = new CmdComment();
        savedComment.setId(1);
        savedComment.setContent(dto.getContent());
        savedComment.setAuthorId(dto.getAuthorId());
        savedComment.setPostId(dto.getPostId());

        when(commentRepository.save(any(CmdComment.class))).thenReturn(savedComment);

        CmdCommentDTO result = commentService.addComment(dto);
        assertEquals(dto.getContent(), result.getContent());
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    public void testUpdateCommentSuccess() throws NotAuthorizedException, NotLoginException {
        CmdComment existingComment = new CmdComment();
        existingComment.setId(1);
        existingComment.setAuthorId(1L);
        existingComment.setContent("Old Comment");

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setId(1);
        dto.setAuthorId(1L);
        dto.setContent("Updated Comment");

        when(commentRepository.findById(1)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(CmdComment.class))).thenReturn(existingComment);

        CmdCommentDTO result = commentService.updateComment(1, dto);
        assertEquals(dto.getContent(), result.getContent());
    }


    @DisplayName("댓글 수정 예외처리 테스트")
    @Test
    public void testUpdateCommentNotAuthorized() {
        CmdComment existingComment = new CmdComment();
        existingComment.setId(1);
        existingComment.setAuthorId(1L);
        existingComment.setContent("Old Comment");

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setId(1);
        dto.setAuthorId(2L);  // 다른 사용자 ID
        dto.setContent("Updated Comment");

        when(commentRepository.findById(1)).thenReturn(Optional.of(existingComment));

        assertThrows(NotAuthorizedException.class, () -> commentService.updateComment(1, dto));
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    public void testDeleteCommentSuccess() throws NotAuthorizedException {
        CmdComment existingComment = new CmdComment();
        existingComment.setId(1);
        existingComment.setAuthorId(1L);

        when(commentRepository.findById(1)).thenReturn(Optional.of(existingComment));

        assertDoesNotThrow(() -> commentService.deleteComment(1));
        verify(commentRepository, times(1)).delete(existingComment);
    }

    @DisplayName("댓글 삭제 예외처리 테스트")
    @Test
    public void testDeleteCommentNotAuthorized() {
        CmdComment existingComment = new CmdComment();
        existingComment.setId(1);
        existingComment.setAuthorId(1L);

        when(commentRepository.findById(1)).thenReturn(Optional.of(existingComment));

        assertThrows(NotAuthorizedException.class, () -> commentService.deleteComment(1));
        verify(commentRepository, never()).delete(existingComment);
    }

}
