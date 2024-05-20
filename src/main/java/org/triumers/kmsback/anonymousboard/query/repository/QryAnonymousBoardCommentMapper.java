package org.triumers.kmsback.anonymousboard.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardCommentDTO;

import java.util.List;

@Mapper
public interface QryAnonymousBoardCommentMapper {
    List<QryAnonymousBoardCommentDTO> findAllAnonymousBoardComment(@Param("anonymousBoardId") int anonymousBoardId, Pageable pageable);
    long countAnonymousBoardComment(@Param("anonymousBoardId") int anonymousBoardId);
}