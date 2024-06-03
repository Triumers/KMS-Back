package org.triumers.kmsback.comment.command.Application.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.CmdComment;
import org.triumers.kmsback.comment.command.Domain.repository.CommentRepository;
import org.triumers.kmsback.common.exception.NotAuthorizedException;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CmdCommentDTO addComment(CmdCommentDTO comment) {
        CmdComment cmdComment = new CmdComment();
        cmdComment.setContent(comment.getContent());
        cmdComment.setAuthorId(comment.getAuthorId());
        cmdComment.setPostId(comment.getPostId());
        cmdComment.setCreatedAt(comment.getCreatedAt());
        cmdComment.setDeletedAt(comment.getDeletedAt());
        commentRepository.save(cmdComment);
        return comment;
    }

    @Override
    public CmdCommentDTO updateComment(Integer commentId, CmdCommentDTO comment) throws NotAuthorizedException {
        CmdComment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if (!existingComment.getAuthorId().equals(comment.getAuthorId())) {
            throw new NotAuthorizedException();
        }
        existingComment.setContent(comment.getContent());
        commentRepository.save(existingComment);
        return comment;
    }

    @Override
    public void deleteComment(Integer commentId, Long userId, boolean isAdmin) throws NotAuthorizedException {
        CmdComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        if (!comment.getAuthorId().equals(userId) && !isAdmin) {
            throw new NotAuthorizedException();
        }
        commentRepository.delete(comment);
    }
}
