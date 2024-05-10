package org.triumers.kmsback.quiz.query.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.service.QryAnswerSubmitterService;

import java.util.List;

@RestController
@RequestMapping("/answer")
public class QryAnswerSubmitterController {

    private QryAnswerSubmitterService qryAnswerSubmitterService;

    public QryAnswerSubmitterController(QryAnswerSubmitterService qryAnswerSubmitterService) {
        this.qryAnswerSubmitterService = qryAnswerSubmitterService;
    }

    /* 설명. 문제 ID로 문제 제출자 조회 */
    @GetMapping("/submitter")
    public List<QryAnswerSubmitterDTO> findQuizByIdAnswerSubmitter(int quizId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = qryAnswerSubmitterService.findSubmitterByQuizId(quizId);
        return qryAnswerSubmitterDTOs;
    }
}
