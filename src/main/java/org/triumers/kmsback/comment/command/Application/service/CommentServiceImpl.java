package org.triumers.kmsback.comment.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.comment.command.Domain.aggregate.entity.Comment;
import org.triumers.kmsback.comment.command.Domain.repository.CommentRepository;
import org.triumers.kmsback.comment.query.dto.CommentDTO;
import org.triumers.kmsback.comment.query.repository.CommentMapper;
import org.triumers.kmsback.post.query.dto.QryPostAndTagsDTO;
import org.triumers.kmsback.post.query.service.QryPostService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QryPostService qryPostService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<CommentDTO> getCommentByPostId(Long postId) {
        return commentMapper.selectCommentsByPostId(postId);
    }

    @Override
    @Transactional
    public Comment createComment(Integer postId, Long authorId, String content) {
        QryPostAndTagsDTO post = qryPostService.findPostById(postId);
        Employee author = employeeService.getEmployeeById(authorId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

}
