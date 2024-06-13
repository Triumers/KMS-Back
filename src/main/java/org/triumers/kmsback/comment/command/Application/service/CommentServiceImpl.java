package org.triumers.kmsback.comment.command.Application.service;



import org.springframework.stereotype.Service;
import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.CmdComment;
import org.triumers.kmsback.comment.command.Domain.repository.CommentRepository;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;

import java.time.LocalDateTime;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AuthService authService;

    public CommentServiceImpl(CommentRepository commentRepository, AuthService authService) {
        this.commentRepository = commentRepository;
        this.authService = authService;
    }

    @Override
    public CmdCommentDTO addComment(CmdCommentDTO comment) throws NotLoginException {

        Employee currentUser = getCurrentUser();
        CmdComment cmdComment = new CmdComment();
        cmdComment.setContent(comment.getContent());
        cmdComment.setAuthorId((long) currentUser.getId());
        cmdComment.setPostId(comment.getPostId());
        cmdComment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(cmdComment);
        return comment;
    }

    @Override
    public CmdCommentDTO updateComment(Integer commentId, CmdCommentDTO comment) throws NotAuthorizedException, NotLoginException {
        CmdComment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        Employee currentUser = getCurrentUser();

        if (existingComment.getAuthorId() != currentUser.getId() && currentUser.getUserRole() != UserRole.ROLE_ADMIN
                && currentUser.getUserRole() != UserRole.ROLE_HR_MANAGER) {
            throw new NotAuthorizedException();
        }
        existingComment.setContent(comment.getContent());
        commentRepository.save(existingComment);
        return comment;
    }

    @Override
    public void deleteComment(Integer commentId) throws NotAuthorizedException, NotLoginException {
        CmdComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        Employee currentUser = getCurrentUser();

        if (comment.getAuthorId() != currentUser.getId() && currentUser.getUserRole() != UserRole.ROLE_ADMIN
                && currentUser.getUserRole() != UserRole.ROLE_HR_MANAGER) {
            throw new NotAuthorizedException();
        }
        commentRepository.delete(comment);
    }


    private Employee getCurrentUser() throws NotLoginException {

        return authService.whoAmI();
    }
}
