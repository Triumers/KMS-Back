package org.triumers.kmsback.quiz.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_quiz_answer_submitter")
@SQLDelete(sql = "UPDATE tbl_quiz_answer_submitter SET DELETED_AT = CURRENT_TIMESTAMP WHERE ID = ?")
@Where(clause = "deleted_at IS NULL")
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

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    public CmdAnswerSubmitter(int id, String answer, String commentary, boolean status, int quizId, int employeeId) {
        this.id = id;
        this.answer = answer;
        Commentary = commentary;
        this.status = status;
        this.quizId = quizId;
        this.employeeId = employeeId;
    }
}

