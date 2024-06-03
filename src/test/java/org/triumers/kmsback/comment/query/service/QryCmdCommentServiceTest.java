package org.triumers.kmsback.comment.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.triumers.kmsback.comment.query.aggregate.entity.QryComment;
import org.triumers.kmsback.comment.query.domain.service.QryCommentServiceImpl;
import org.triumers.kmsback.comment.query.repository.CommentMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class QryCmdCommentServiceTest {

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private QryCommentServiceImpl commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("특정 사용자 ID로 댓글 조회 테스트")
    @Test
    public void testGetCommentsByUserId() {
        List<QryComment> comments = new ArrayList<>();
        QryComment comment = new QryComment();
        comment.setId(1L);
        comment.setAuthorId(1L);
        comment.setContent("User Comment");
        comments.add(comment);

        when(commentMapper.selectCommentsByUserId(1L)).thenReturn(comments);

        List<QryComment> result = commentService.getCommentsByUserId(1L);
        assertEquals(1, result.size());
        assertEquals("User Comment", result.get(0).getContent());
    }

    @Test
    @DisplayName("특정 게시물 ID로 댓글 조회 테스트")
    public void testGetCommentsByPostId() {
        List<QryComment> comments = new ArrayList<>();
        QryComment comment = new QryComment();
        comment.setId(1L);
        comment.setPostId(1L);
        comment.setContent("Post Comment");
        comments.add(comment);

        when(commentMapper.selectCommentsByPostId(1L)).thenReturn(comments);

        List<QryComment> result = commentService.getCommentsByPostId(1L);
        assertEquals(1, result.size());
        assertEquals("Post Comment", result.get(0).getContent());
    }
}
