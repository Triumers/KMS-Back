package org.triumers.kmsback.comment.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.triumers.kmsback.comment.query.aggregate.entity.QryComment;


import java.util.List;

@Mapper
public interface CommentMapper {
//    @Select("SELECT id, content, created_at AS createdAt, author_id AS authorId, post_id AS postId FROM tbl_comment WHERE post_id = #{postId} AND deleted_at IS NULL")
    List<QryComment> selectCommentsByPostId(@Param("postId") Long postId);
    List<QryComment> selectCommentsByUserId(@Param("userId") Long userId);

}
