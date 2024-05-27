package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;

public interface CmdAnonymousBoardCommentService {

    CmdAnonymousBoardCommentDTO saveAnonymousBoardComment(CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO);
    void deleteAnonymousBoardComment(int id);
}
