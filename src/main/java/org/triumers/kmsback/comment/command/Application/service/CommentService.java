package org.triumers.kmsback.comment.command.Application.service;

import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.Comment;
import org.triumers.kmsback.comment.query.dto.CommentDTO;
import org.triumers.kmsback.common.translation.entity.Post;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getCommentByPostId(Long postId);

    Comment createComment(Integer postId, Long authorId, String content);

    Comment updateComment(Long commentId, String content);

    void deleteComment(Long commentId);
}
