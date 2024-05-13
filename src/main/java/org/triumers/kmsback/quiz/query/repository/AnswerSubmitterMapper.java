package org.triumers.kmsback.quiz.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryAnswerSubmitter;

import java.util.List;

@Mapper
public interface AnswerSubmitterMapper {
    List<QryAnswerSubmitter> findSubmitterByQuizId(int quizId);
}
