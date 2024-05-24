package org.triumers.kmsback.quiz.query.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryAnswerSubmitter;

@Mapper
public interface AnswerSubmitterMapper {
    List<QryAnswerSubmitter> findSubmitterByQuizId(int quizId);

    List<QryAnswerSubmitter> findSubmitByEmployeeId(int employeeId);
}