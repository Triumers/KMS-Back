package org.triumers.kmsback.comment.command.Application.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.comment.command.Domain.repository.CommentRepository;
import org.triumers.kmsback.comment.query.aggregate.entity.QryComment;
import org.triumers.kmsback.comment.query.domain.service.QryCommentService;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.query.service.QryAuthService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CmdCommentServiceTest {

    @Autowired
    private LoggedInUser loggedInUser;

    @Autowired
    private CommentService commentService;

    @Autowired
    private QryCommentService qryCommentService;

    @Autowired
    private QryAuthService qryAuthService;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("댓글 추가 테스트")
    @Test
    public void testAddComment() throws NotLoginException, WrongInputValueException {

        // given
        loggedInUser.setting();
        long userId = qryAuthService.myInfo().getId();

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setContent("Test Comment");
        dto.setPostId(1L);

        // when
        commentService.addComment(dto);

        // then
        List<QryComment> result = qryCommentService.getCommentsByUserId(userId);
        assertFalse(result.isEmpty());
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    public void testUpdateCommentSuccess() throws NotAuthorizedException, NotLoginException, WrongInputValueException {

        // given
        loggedInUser.setting();
        long userId = qryAuthService.myInfo().getId();

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setContent("Old Comment");
        dto.setPostId(1L);
        commentService.addComment(dto);

        long commentId = qryCommentService.getCommentsByUserId(userId).get(0).getId();

        CmdCommentDTO newComment = new CmdCommentDTO();
        newComment.setContent("New Comment");

        // when
        commentService.updateComment((int) commentId, newComment);

        // then
        assertEquals(commentRepository.findById((int) commentId).getContent(), newComment.getContent());
    }


    @DisplayName("댓글 수정 예외처리 테스트")
    @Test
    public void testUpdateCommentNotAuthorized() throws NotLoginException, WrongInputValueException, NotAuthorizedException {

        // given
        loggedInUser.settingHrManager();
        long userId = qryAuthService.myInfo().getId();

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setContent("Origin Auth Comment");
        dto.setPostId(1L);
        commentService.addComment(dto);

        long commentId = qryCommentService.getCommentsByUserId(userId).get(0).getId();

        CmdCommentDTO newComment = new CmdCommentDTO();
        newComment.setContent("New Comment");

        // when
        loggedInUser.setting();

        // then
        assertThrows(NotAuthorizedException.class, () -> commentService.updateComment((int) commentId, newComment));
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    public void testDeleteCommentSuccess() throws NotAuthorizedException, NotLoginException, WrongInputValueException {

        // given
        loggedInUser.setting();
        long userId = qryAuthService.myInfo().getId();

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setContent("Target Comment");
        dto.setPostId(1L);
        commentService.addComment(dto);

        long commentId = qryCommentService.getCommentsByUserId(userId).get(0).getId();

        // when
        commentService.deleteComment((int) commentId);

        // then
        assertNull(commentRepository.findById((int) commentId));
    }

    @DisplayName("댓글 삭제 예외처리 테스트")
    @Test
    public void testDeleteCommentNotAuthorized() throws NotLoginException, WrongInputValueException {

        // given
        loggedInUser.settingHrManager();
        long userId = qryAuthService.myInfo().getId();

        CmdCommentDTO dto = new CmdCommentDTO();
        dto.setContent("Origin Auth Comment");
        dto.setPostId(1L);
        commentService.addComment(dto);

        long commentId = qryCommentService.getCommentsByUserId(userId).get(0).getId();

        // when
        loggedInUser.setting();

        // then
        assertNotNull(commentRepository.findById((int) commentId));
    }

}
