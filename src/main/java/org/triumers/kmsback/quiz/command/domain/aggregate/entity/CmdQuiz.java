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
@Table(name = "tbl_quiz")
@SQLDelete(sql = "UPDATE tbl_quiz SET DELETED_AT = CURRENT_TIMESTAMP WHERE ID = ?")
@Where(clause = "deleted_at IS NULL")
public class CmdQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ANSWER")
    private String answer;

    @Column(name = "COMMENTARY")
    private String commentary;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "QUESTIONER_ID")
    private int questionerId;

    @Column(name = "POST_ID")
    private int postId;

    @Column(name = "TAB_ID")
    private int topTapId;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    public CmdQuiz(int id, String content, String answer, String commentary, boolean status, int questionerId, int postId, int topTapId) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.commentary = commentary;
        this.status = status;
        this.questionerId = questionerId;
        this.postId = postId;
        this.topTapId = topTapId;
    }
}
