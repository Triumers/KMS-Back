package org.triumers.kmsback.quiz.query.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryAnswerSubmitter;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.repository.AnswerSubmitterMapper;

@ExtendWith({MockitoExtension.class})
class QryAnswerSubmitterServiceImplTest {
    @Mock
    private AnswerSubmitterMapper answerSubmitterMapper;
    @InjectMocks
    private QryAnswerSubmitterServiceImpl qryAnswerSubmitterService;
    private QryAnswerSubmitter submitter;

    QryAnswerSubmitterServiceImplTest() {
    }

    @BeforeEach
    void setUp() {
        this.submitter = new QryAnswerSubmitter();
        this.submitter.setId(1);
        this.submitter.setAnswer("답변");
        this.submitter.setCommentary("참조");
        this.submitter.setStatus(true);
        this.submitter.setQuizId(1);
        this.submitter.setEmployeeId(1);
    }

    @DisplayName("문제 ID로 문제 답 제출자 조회")
    @Test
    void findSubmitterByQuizId() {
        List<QryAnswerSubmitter> submitters = new ArrayList();
        submitters.add(this.submitter);
        Mockito.when(this.answerSubmitterMapper.findSubmitterByQuizId(1)).thenReturn(submitters);
        List<QryAnswerSubmitterDTO> expectedSubmitters = this.qryAnswerSubmitterService.findSubmitterByQuizId(1);
        Assertions.assertEquals(1, expectedSubmitters.size());
        QryAnswerSubmitterDTO expectedSubmitter = (QryAnswerSubmitterDTO)expectedSubmitters.get(0);
        Assertions.assertEquals(this.submitter.getId(), expectedSubmitter.getId());
        Assertions.assertEquals(this.submitter.getAnswer(), expectedSubmitter.getAnswer());
        Assertions.assertEquals(this.submitter.getQuizId(), expectedSubmitter.getQuizId());
        Assertions.assertEquals(this.submitter.getEmployeeId(), expectedSubmitter.getEmployeeId());
    }

    @DisplayName("사원 ID로 제출 내역 조회")
    @Test
    void findSubmitByEmployeeId() {
        List<QryAnswerSubmitter> submitters = new ArrayList();
        submitters.add(this.submitter);
        Mockito.when(this.answerSubmitterMapper.findSubmitByEmployeeId(1)).thenReturn(submitters);
        List<QryAnswerSubmitterDTO> expectedSubmitters = this.qryAnswerSubmitterService.findSubmitByEmployeeId(1);
        Assertions.assertEquals(1, expectedSubmitters.size());
        QryAnswerSubmitterDTO expectedSubmitter = (QryAnswerSubmitterDTO)expectedSubmitters.get(0);
        Assertions.assertEquals(this.submitter.getId(), expectedSubmitter.getId());
        Assertions.assertEquals(this.submitter.getAnswer(), expectedSubmitter.getAnswer());
        Assertions.assertEquals(this.submitter.getQuizId(), expectedSubmitter.getQuizId());
        Assertions.assertEquals(this.submitter.getEmployeeId(), expectedSubmitter.getEmployeeId());
        Assertions.assertEquals(this.submitter.getCreatedAt(), expectedSubmitter.getCreatedAt());
    }
}
