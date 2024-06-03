package org.triumers.kmsback.comment.command.Application.service;

import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.common.exception.NotAuthorizedException;

public interface CommentService {
    CmdCommentDTO addComment(CmdCommentDTO comment);
    CmdCommentDTO updateComment(Integer commentId, CmdCommentDTO comment) throws NotAuthorizedException;
    void deleteComment(Integer commentId, Long userId, boolean isAdmin) throws NotAuthorizedException;
}
