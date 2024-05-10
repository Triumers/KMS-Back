package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardCommentDTO;

public interface CmdAnonymousBoardCommentService {

    Page<CmdAnonymousBoardCommentDTO> findAllAnonymousBoardComment(int anonymousBoardId, Pageable pageable);

    CmdAnonymousBoardCommentDTO saveAnonymousBoardComment(CmdAnonymousBoardCommentDTO cmdAnonymousBoardCommentDTO);

    void deleteAnonymousBoardComment(int id);
}
