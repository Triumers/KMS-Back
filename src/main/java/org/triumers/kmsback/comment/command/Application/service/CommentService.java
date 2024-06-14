package org.triumers.kmsback.comment.command.Application.service;

import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.common.exception.NotLoginException;

public interface CommentService {
    CmdCommentDTO addComment(CmdCommentDTO comment) throws NotLoginException;
    CmdCommentDTO updateComment(Integer commentId, CmdCommentDTO comment) throws NotAuthorizedException, NotLoginException;

    void deleteComment(Integer commentId) throws NotAuthorizedException, NotLoginException;
}
