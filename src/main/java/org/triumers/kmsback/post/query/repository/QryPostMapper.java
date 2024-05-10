package org.triumers.kmsback.post.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;

import java.util.List;

@Mapper
public interface QryPostMapper {
    List<QryPostAndTag> selectTabPostList(int tabId);

    QryPostAndTag selectPostById(int postId);

    List<QryPostAndTag> selectHistoryListByOriginId(int originId);
}
