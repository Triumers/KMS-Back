package org.triumers.kmsback.quiz.query.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.quiz.query.aggregate.entity.QryAnswerSubmitter;
import org.triumers.kmsback.quiz.query.dto.QryAnswerSubmitterDTO;
import org.triumers.kmsback.quiz.query.repository.AnswerSubmitterMapper;

@Service
public class QryAnswerSubmitterServiceImpl implements QryAnswerSubmitterService {
    @Autowired
    private final AnswerSubmitterMapper answerSubmitterMapper;

    @Autowired
    public QryAnswerSubmitterServiceImpl(AnswerSubmitterMapper answerSubmitterMapper) {
        this.answerSubmitterMapper = answerSubmitterMapper;
    }

    public List<QryAnswerSubmitterDTO> findSubmitterByQuizId(int quizId) {
        List<QryAnswerSubmitter> qryAnswerSubmitters = this.answerSubmitterMapper.findSubmitterByQuizId(quizId);
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = new ArrayList();
        Iterator var4 = qryAnswerSubmitters.iterator();

        while(var4.hasNext()) {
            QryAnswerSubmitter qryAnswerSubmitter = (QryAnswerSubmitter)var4.next();
            QryAnswerSubmitterDTO qryAnswerSubmitterDTO = new QryAnswerSubmitterDTO();
            qryAnswerSubmitterDTO.setId(qryAnswerSubmitter.getId());
            qryAnswerSubmitterDTO.setAnswer(qryAnswerSubmitter.getAnswer());
            qryAnswerSubmitterDTO.setQuizId(qryAnswerSubmitter.getQuizId());
            qryAnswerSubmitterDTO.setEmployeeId(qryAnswerSubmitter.getEmployeeId());
            qryAnswerSubmitterDTO.setCreatedAt(qryAnswerSubmitter.getCreatedAt());
            qryAnswerSubmitterDTOs.add(qryAnswerSubmitterDTO);
        }

        return qryAnswerSubmitterDTOs;
    }

    public List<QryAnswerSubmitterDTO> findSubmitByEmployeeId(int employeeId) {
        List<QryAnswerSubmitter> qryAnswerSubmitters = this.answerSubmitterMapper.findSubmitByEmployeeId(employeeId);
        List<QryAnswerSubmitterDTO> qryAnswerSubmitterDTOs = new ArrayList();
        Iterator var4 = qryAnswerSubmitters.iterator();

        while(var4.hasNext()) {
            QryAnswerSubmitter qryAnswerSubmitter = (QryAnswerSubmitter)var4.next();
            QryAnswerSubmitterDTO qryAnswerSubmitterDTO = new QryAnswerSubmitterDTO();
            qryAnswerSubmitterDTO.setId(qryAnswerSubmitter.getId());
            qryAnswerSubmitterDTO.setAnswer(qryAnswerSubmitter.getAnswer());
            qryAnswerSubmitterDTO.setQuizId(qryAnswerSubmitter.getQuizId());
            qryAnswerSubmitterDTO.setEmployeeId(qryAnswerSubmitter.getEmployeeId());
            qryAnswerSubmitterDTO.setCreatedAt(qryAnswerSubmitter.getCreatedAt());
            qryAnswerSubmitterDTOs.add(qryAnswerSubmitterDTO);
        }

        return qryAnswerSubmitterDTOs;
    }
}
