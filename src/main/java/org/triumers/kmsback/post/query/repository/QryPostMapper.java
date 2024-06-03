package org.triumers.kmsback.post.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.post.query.aggregate.entity.QryLike;
import org.triumers.kmsback.post.query.aggregate.entity.QryPostAndTag;
import org.triumers.kmsback.post.query.aggregate.entity.QryTag;
import org.triumers.kmsback.post.query.aggregate.vo.QryRequestPost;

import java.util.List;

@Mapper
public interface QryPostMapper {
    List<QryPostAndTag> selectTabPostList(QryRequestPost request, Pageable pageable);

    QryPostAndTag selectPostById(int postId);

    List<QryPostAndTag> selectHistoryListByOriginId(int originId);

    List<QryLike> selectLikeListByPostId(int postId);

    List<QryTag> selectTagList(int postId);

    Boolean selectIsEditingByPostId(int postId);

    List<Integer> selectParticipantsListByOriginId(int postId);

    Integer originAuthorId(Integer postId);

    Integer selectPostCount(QryRequestPost request, Pageable pageable);

    List<QryPostAndTag> selectMyPostList(int employeeId);

    List<QryPostAndTag> selectMyLikeList(int employeeId);

    List<QryPostAndTag> selectMyFavoriteList(int employeeId);

    Boolean selectIsLikedByPostId(int postId, int employeeId);

    Boolean selectIsFavoriteByPostId(int postId, int employeeId);
}
