package org.triumers.kmsback.quiz.query.service;

import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;

import java.util.List;

public interface QryAnswerSubmitterService {

    List<QryAnswerSubmitterDTO> findSubmitterByQuizId(int quizId);
}
