package org.triumers.kmsback.common.translation.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_post_jpn")
public class PostJpn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Post post;
}
