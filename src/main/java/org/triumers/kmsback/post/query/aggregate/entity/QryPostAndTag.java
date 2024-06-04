package org.triumers.kmsback.post.query.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QryPostAndTag {
    private Integer id;
    private Boolean isEditing;
    private String title;
    private String content;
    private String postImg;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Integer authorId;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
    private Integer categoryId;
    private List<QryTag> tags;
}
