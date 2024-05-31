package org.triumers.kmsback.quiz.query.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.quiz.query.dto.QryQuizDTO;
import org.triumers.kmsback.quiz.query.service.QryQuizService;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QryQuizController {

    private final QryQuizService qryQuizService;

    public QryQuizController(QryQuizService qryQuizService) {
        this.qryQuizService = qryQuizService;
    }

    /* 설명. 퀴즈 상태로 퀴즈 목록 조회 */
    @GetMapping("/status")
    public ResponseEntity<List<QryQuizDTO>> findQuizByStatus(@RequestParam boolean status) {
        List<QryQuizDTO> quizDTOList = qryQuizService.findQuizByStatus(status);
        return ResponseEntity.ok(quizDTOList);
    }

    /* 설명. 퀴즈 ID로 퀴즈 문제, 정답, 해설, 참조 게시글 조회 */
    @GetMapping("/contents")
    public ResponseEntity<QryQuizDTO> findQuizById(@RequestParam int id) {
        QryQuizDTO quizDTO = qryQuizService.findQuizById(id);
        return ResponseEntity.ok(quizDTO);
    }

    /* 설명. 게시글 ID로 퀴즈 문제, 정답, 해설, 참조 게시글 조회 */
    @GetMapping("/exist")
    public ResponseEntity<QryQuizDTO> findQuizByPostId(@RequestParam int postId) {
        QryQuizDTO quizDTO = qryQuizService.findQuizByPostId(postId);
        return ResponseEntity.ok(quizDTO);
    }
}
