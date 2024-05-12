package org.triumers.kmsback.anonymousboard.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;

public interface QryAnonymousBoardCommentService {
    Page<QryAnonymousBoardCommentDTO> findAllAnonymousBoardComment(int anonymousBoardId, Pageable pageable);
}