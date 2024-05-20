package org.triumers.kmsback.quiz.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_quiz_answer_submitter")
public class CmdAnswerSubmitter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "COMMENTARY")
    private String Commentary;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "QUIZ_ID")
    private int quizId;

    @Column(name = "EMPLOYEE_ID")
    private int employeeId;
}
