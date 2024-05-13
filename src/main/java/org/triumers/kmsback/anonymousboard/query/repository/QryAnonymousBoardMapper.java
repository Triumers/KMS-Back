package org.triumers.kmsback.anonymousboard.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.query.dto.QryAnonymousBoardDTO;

import java.util.List;

@Mapper
public interface QryAnonymousBoardMapper {
    List<QryAnonymousBoardDTO> findAllAnonymousBoard(Pageable pageable);
    long countAllAnonymousBoard();
    List<QryAnonymousBoardDTO> searchAnonymousBoardByTitle(String title, Pageable pageable);
    long countAnonymousBoardByTitle(String title);
    List<QryAnonymousBoardDTO> searchAnonymousBoardByContent(String content, Pageable pageable);
    long countAnonymousBoardByContent(String content);
    List<QryAnonymousBoardDTO> searchAnonymousBoardByTitleAndContent(String keyword, Pageable pageable);
    long countAnonymousBoardByTitleAndContent(String keyword);
    QryAnonymousBoardDTO findAnonymousBoardById(int id);
}