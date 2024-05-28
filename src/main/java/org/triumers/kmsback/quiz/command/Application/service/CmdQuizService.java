package org.triumers.kmsback.quiz.command.Application.service;

import org.triumers.kmsback.quiz.command.Application.dto.CmdQuizDTO;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestQuizVo;
import org.triumers.kmsback.quiz.command.domain.aggregate.vo.CmdRequestRemoveQuizVo;

public interface CmdQuizService {
    CmdQuizDTO registQuiz(CmdRequestQuizVo request);

    CmdQuizDTO editQuiz(CmdRequestQuizVo request);

    CmdQuizDTO removeQuiz(int id);
}
