package org.triumers.kmsback.quiz.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryQuiz;
import org.triumers.kmsback.quiz.query.dto.QryQuizDTO;
import org.triumers.kmsback.quiz.query.repository.QuizMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryQuizServiceImpl implements QryQuizService {

    @Autowired
    private final QuizMapper quizMapper;

    @Autowired
    public QryQuizServiceImpl(QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    @Override
    public List<QryQuizDTO> findQuizByStatus(boolean status) {
        List<QryQuiz> qryQuizs = quizMapper.findQuizByStatus(status);
        List<QryQuizDTO> qryQuizDTOs = new ArrayList<>();

        for (QryQuiz qryQuiz : qryQuizs) {
            QryQuizDTO qryQuizDTO = new QryQuizDTO();
            qryQuizDTO.setId(qryQuiz.getId());
            qryQuizDTO.setContent(qryQuiz.getContent());
            qryQuizDTO.setAnswer(qryQuiz.getAnswer());
            qryQuizDTO.setCommentary(qryQuiz.getCommentary());
            qryQuizDTO.setStatus(qryQuiz.isStatus());
            qryQuizDTO.setQuestionerId(qryQuiz.getQuestionerId());
            qryQuizDTO.setPostId(qryQuiz.getPostId());
            qryQuizDTO.setTapId(qryQuiz.getTapId());

            qryQuizDTOs.add(qryQuizDTO);
        }
        return qryQuizDTOs;
    }

    @Override
    public QryQuizDTO findQuizById(int id) {
        QryQuiz qryQuiz = quizMapper.findQuizById(id);
        QryQuizDTO qryQuizDTO = new QryQuizDTO();

        qryQuizDTO.setId(qryQuiz.getId());
        qryQuizDTO.setContent(qryQuiz.getContent());
        qryQuizDTO.setAnswer(qryQuiz.getAnswer());
        qryQuizDTO.setCommentary(qryQuiz.getCommentary());
        qryQuizDTO.setStatus(qryQuiz.isStatus());
        qryQuizDTO.setQuestionerId(qryQuiz.getQuestionerId());
        qryQuizDTO.setPostId(qryQuiz.getPostId());
        qryQuizDTO.setTapId(qryQuiz.getTapId());

        return qryQuizDTO;
    }

}
