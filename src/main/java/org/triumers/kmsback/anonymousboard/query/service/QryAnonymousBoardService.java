package org.triumers.kmsback.anonymousboard.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;

public interface QryAnonymousBoardService {
    Page<QryAnonymousBoardDTO> findAllAnonymousBoard(Pageable pageable);
    Page<QryAnonymousBoardDTO> searchAnonymousBoard(String keyword, String type, Pageable pageable);
    QryAnonymousBoardDTO findAnonymousBoardById(int id);
}