package org.triumers.kmsback.post.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.employee.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.dto.QryLikeDTO;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;

import java.util.List;

public interface QryPostService {

    Page<QryPostAndTagsDTO> findPostListByTab(int tabId, Pageable pageable);

    QryPostAndTagsDTO findPostById(int postId);

    List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId);

    List<CmdEmployeeDTO> findLikeListByPostId(int postId);
}
