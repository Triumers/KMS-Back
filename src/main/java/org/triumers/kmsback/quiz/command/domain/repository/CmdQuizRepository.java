package org.triumers.kmsback.quiz.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.quiz.command.domain.aggregate.entity.CmdQuiz;

@Repository
public interface CmdQuizRepository extends JpaRepository<CmdQuiz, Long> {
    Object findById(int id);
}
