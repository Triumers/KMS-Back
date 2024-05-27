package org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_anonymous_board_comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CmdAnonymousBoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname = "익명";

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "MAC_ADDRESS", nullable = false)
    private String macAddress;

    @ManyToOne
    @JoinColumn(name = "ANONYMOUS_BOARD_ID", nullable = false)
    private CmdAnonymousBoard anonymousBoard;

    public CmdAnonymousBoardComment(String nickname, String content, String macAddress, CmdAnonymousBoard anonymousBoard) {
        this.nickname = nickname;
        this.content = content;
        this.macAddress = macAddress;
        this.anonymousBoard = anonymousBoard;
    }
}
