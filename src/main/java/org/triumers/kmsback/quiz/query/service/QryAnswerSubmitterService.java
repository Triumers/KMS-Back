package org.triumers.kmsback.quiz.query.service;

import java.util.List;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;

public interface QryAnswerSubmitterService {
    List<QryAnswerSubmitterDTO> findSubmitterByQuizId(int quizId);

    List<QryAnswerSubmitterDTO> findSubmitByEmployeeId(int employeeId);
}
