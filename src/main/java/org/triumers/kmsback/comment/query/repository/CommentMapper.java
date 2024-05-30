package org.triumers.kmsback.comment.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.triumers.kmsback.comment.query.dto.CommentDTO;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT id, content, created_at AS createdAt, author_id AS authorId, post_id AS postId FROM tbl_comment WHERE post_id = #{postId} AND deleted_at IS NULL")
    List<CommentDTO> selectCommentsByPostId(@Param("postId") Long postId);
}
