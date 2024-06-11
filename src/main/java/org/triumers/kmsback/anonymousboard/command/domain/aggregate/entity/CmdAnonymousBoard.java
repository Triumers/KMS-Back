package org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_anonymous_board")
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "MAC_ADDRESS", nullable = false)
    private String macAddress;

    @OneToMany(mappedBy = "anonymousBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CmdAnonymousBoardComment> comments = new ArrayList<>();

    public CmdAnonymousBoard(String title, String content, String macAddress) {
        this.title = title;
        this.content = content;
        this.macAddress = macAddress;
    }

    public CmdAnonymousBoard(int id, String nickname, String title, String content, LocalDateTime createdDate, String macAddress) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.macAddress = macAddress;
    }
}
