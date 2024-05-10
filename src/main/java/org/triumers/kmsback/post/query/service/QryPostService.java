package org.triumers.kmsback.post.query.service;

import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;

import java.util.List;

public interface QryPostService {

    List<QryPostAndTagsDTO> findPostListByTab(int tabId);

    QryPostAndTagsDTO findPostById(int postId);

    List<QryPostAndTagsDTO> findHistoryListByOriginId(int originId);
}
