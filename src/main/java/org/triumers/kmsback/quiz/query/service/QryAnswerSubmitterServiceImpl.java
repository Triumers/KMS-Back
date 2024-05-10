package org.triumers.kmsback.quiz.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryAnswerSubmitter;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.repository.AnswerSubmitterMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class QryAnswerSubmitterServiceImpl implements QryAnswerSubmitterService {

    @Autowired
    private final AnswerSubmitterMapper answerSubmitterMapper;

    @Autowired
    public QryAnswerSubmitterServiceImpl(AnswerSubmitterMapper answerSubmitterMapper) {
        this.answerSubmitterMapper = answerSubmitterMapper;
    }

    @Override
    public List<QryAnswerSubmitterDTO> findSubmitterByQuizId(int quizId) {
        List<QryAnswerSubmitter> qryAnswerSubmitters = answerSubmitterMapper.findSubmitterByQuizId(quizId);
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = new ArrayList<>();

        for (QryAnswerSubmitter qryAnswerSubmitter : qryAnswerSubmitters) {
            QryAnswerSubmitterDTO qryAnswerSubmitterDTO = new QryAnswerSubmitterDTO();
            qryAnswerSubmitterDTO.setId(qryAnswerSubmitter.getId());
            qryAnswerSubmitterDTO.setAnswer(qryAnswerSubmitter.getAnswer());
            qryAnswerSubmitterDTO.setQuizId(qryAnswerSubmitter.getQuizId());
            qryAnswerSubmitterDTO.setEmployeeId(qryAnswerSubmitter.getEmployeeId());

            qryAnswerSubmitterDTOs.add(qryAnswerSubmitterDTO);
        }
        return qryAnswerSubmitterDTOs;
    }
}
