package org.triumers.kmsback.common.translation.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PostEng postEng;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PostCn postCn;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private PostJpn postJpn;

    @Column(name = "AUTHOR_ID", nullable = false)
    private Integer authorId;
}
