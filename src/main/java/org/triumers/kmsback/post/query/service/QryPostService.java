package org.triumers.kmsback.post.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

public interface QryPostService {

    Page<QryPostAndTagsDTO> findPostListByTab(QryRequestPost request, Pageable pageable) throws NotLoginException, WrongInputValueException;

    Page<QryPostAndTagsDTO> findAllPostListByEmployee(QryRequestPost request, Pageable pageable) throws NotLoginException, WrongInputValueException;

    QryPostAndTagsDTO findPostById(int postId) throws NotLoginException, WrongInputValueException;

    List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId) throws NotLoginException, WrongInputValueException;

    List<QryEmployeeDTO> findLikeListByPostId(int postId) throws WrongInputValueException;

    Boolean getIsEditingById(int postId);

    List<QryPostAndTagsDTO> findPostByEmployeeId(int employeeId);

    List<QryPostAndTagsDTO> findLikePostByEmployeeId(int employeeId);

    List<QryPostAndTagsDTO> findFavoritePostByEmployeeId(int employeeId);

    Boolean findIsLikedByPostId(int postId) throws NotLoginException;

    Boolean findIsFavoriteByPostId(int postId) throws NotLoginException;
}
