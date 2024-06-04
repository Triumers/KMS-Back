package org.triumers.kmsback.quiz.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryQuiz;

import java.util.List;

@Mapper
public interface QuizMapper {

    List<QryQuiz> findQuizByStatus(boolean status);

    QryQuiz findQuizById(int id);

    QryQuiz findQuizByPostId(int postId);
}
