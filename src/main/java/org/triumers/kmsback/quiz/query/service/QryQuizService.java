package org.triumers.kmsback.quiz.query.service;

import org.triumers.kmsback.quiz.query.dto.QryQuizDTO;

import java.util.List;

public interface QryQuizService {

    List<QryQuizDTO> findQuizByStatus(boolean status);

    QryQuizDTO findQuizById(int id);
}
