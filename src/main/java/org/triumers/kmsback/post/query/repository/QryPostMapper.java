package org.triumers.kmsback.post.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.post.query.aggregate.entity.QryLike;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.entity.QryTag;

import java.util.List;

@Mapper
public interface QryPostMapper {
    List<QryPostAndTag> selectTabPostList(int tabId, Pageable pageable);

    QryPostAndTag selectPostById(int postId);

    List<QryPostAndTag> selectHistoryListByOriginId(int originId);

    List<QryLike> selectLikeListByPostId(int postId);

    List<QryTag> selectTagList(int postId);

    long countTabPostList(int tabId);
}
