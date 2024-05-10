package org.triumers.kmsback.quiz.query.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.quiz.query.dto.QryQuizDTO;
import org.triumers.kmsback.quiz.query.service.QryQuizService;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QryQuizController {

    private QryQuizService qryQuizService;

    public QryQuizController(QryQuizService qryQuizService) {
        this.qryQuizService = qryQuizService;
    }


    /* 설명. 퀴즈상태로 퀴즈목록 조회 */
    @GetMapping("/status")
    public List<QryQuizDTO> findQuizByStatus(boolean status) {
        List<QryQuizDTO> qryQuizDTOS = qryQuizService.findQuizByStatus(status);
        return qryQuizDTOS;
    }

    /* 설명. 퀴즈 ID로 퀴즈 문제, 정답, 해설, 참조 게시글 조회 */
    public QryQuizDTO findQuizById(int id) {
        QryQuizDTO qryQuizDTO = qryQuizService.findQuizById(id);
        return qryQuizDTO;
    }


    /* 설명. 문제 ID로 문제의 정답자 조회 */

}
