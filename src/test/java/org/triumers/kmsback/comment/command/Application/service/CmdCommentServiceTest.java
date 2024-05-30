//package org.triumers.kmsback.comment.command.Application.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import org.triumers.kmsback.comment.command.Application.dto.CmdCommentDTO;
//import org.triumers.kmsback.comment.command.Domain.aggregate.entity.CmdComment;
//import org.triumers.kmsback.comment.command.Domain.repository.CmdCommentRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//public class CmdCommentServiceTest {
//
//    @Autowired
//    private CmdCommentService cmdCommentService;
//
//    @Autowired
//    private CmdCommentRepository commentRepository;
//
//    @Test
//    public void testAddComment() {
//        CmdCommentDTO dto = new CmdCommentDTO();
//        dto.setAuthorId(1L);
//        dto.setPostId(1L);
//        dto.setContent("This is a test comment");
//
//        cmdCommentService.addComment(dto);
//
//        CmdComment comment = commentRepository.findAll().get(0);
//        assertThat(comment.getAuthorId()).isEqualTo(dto.getAuthorId());
//        assertThat(comment.getPostId()).isEqualTo(dto.getPostId());
//        assertThat(comment.getContent()).isEqualTo(dto.getContent());
//    }
//
//    @Test
//    public void testUpdateComment() {
//        CmdComment comment = new CmdComment();
//        comment.setAuthorId(1L);
//        comment.setPostId(1L);
//        comment.setContent("Original content");
//        commentRepository.save(comment);
//
//        CmdCommentDTO dto = new CmdCommentDTO();
//        dto.setAuthorId(1L);
//        dto.setContent("Update content");
//
//        cmdCommentService.updateComment(comment.getId(), dto);
//
//        CmdComment updateComment = commentRepository.findById(comment.getId()).orElse(null);
//        assertThat(updateComment).isNotNull();
//        assertThat(updateComment.getContent()).isEqualTo("Updated content");
//    }
//
//    @Test
//    public void testDeleteComment() {
//        CmdComment comment = new CmdComment();
//        comment.setAuthorId(1L);
//        comment.setPostId(1L);
//        comment.setContent("Content to be deleted");
//        commentRepository.save(comment);
//
//        cmdCommentService.deleteComment(comment.getId(), 1L);
//
//        CmdComment deletedComment = commentRepository.findById(comment.getId()).orElse(null);
//        assertThat(deletedComment).isNotNull();
//        assertThat(deletedComment.getDeletedAt()).isNotNull();
//    }
//}
