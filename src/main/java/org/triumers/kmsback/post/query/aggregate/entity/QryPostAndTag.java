package org.triumers.kmsback.post.query.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QryPostAndTag {
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
    private List<QryTag> tags;
}
