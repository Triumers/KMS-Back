package org.triumers.kmsback.quiz.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tbl_quiz")
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
}
