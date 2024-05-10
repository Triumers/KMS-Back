package org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tbl_anonymous_board")
public class CmdAnonymousBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname = "익명";

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "MAC_ADDRESS", nullable = false)
    private String macAddress;

    public CmdAnonymousBoard(String title, String content, String macAddress) {
        this.title = title;
        this.content = content;
        this.macAddress = macAddress;
    }

}
