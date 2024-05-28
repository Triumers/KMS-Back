package org.triumers.kmsback.quiz.query.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.service.QryAnswerSubmitterService;

@RestController
@RequestMapping("/answer")
public class QryAnswerSubmitterController {
    private QryAnswerSubmitterService qryAnswerSubmitterService;

    public QryAnswerSubmitterController(QryAnswerSubmitterService qryAnswerSubmitterService) {
        this.qryAnswerSubmitterService = qryAnswerSubmitterService;
    }

    /* 설명. 문제 ID로 문제 답 제출자 조회 */
    @GetMapping("/submitter/{quizId}")
    public List<QryAnswerSubmitterDTO> findQuizByIdAnswerSubmitter(@PathVariable int quizId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = this.qryAnswerSubmitterService.findSubmitterByQuizId(quizId);
        return qryAnswerSubmitterDTOs;
    }

    /* 설명. 사원 ID로 제출 내역 조회 */
    @GetMapping("/submit-list/{employeeId}")
    public List<QryAnswerSubmitterDTO> findSubmitByEmployeeId(@PathVariable int employeeId) {
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = this.qryAnswerSubmitterService.findSubmitByEmployeeId(employeeId);
        return qryAnswerSubmitterDTOs;
    }
}
