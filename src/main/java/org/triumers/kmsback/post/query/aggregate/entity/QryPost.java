package org.triumers.kmsback.post.query.aggregate.entity;

import lombok.Data;
import java.time.LocalDate;


@Data
public class QryPost {
    private Integer id;
    private Boolean isEditing;
    private String title;
    private String content;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
}
