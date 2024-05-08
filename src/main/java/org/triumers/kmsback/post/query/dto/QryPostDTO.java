package org.triumers.kmsback.post.query.dto;

import org.triumers.kmsback.employee.query.aggregate.entity.QryEmployee;

import java.time.LocalDate;
import java.util.List;

public class QryPostDTO {

    private Integer id;
    private Boolean isEditing;
    private String title;
    private String content;
    private LocalDate createdAt;
    private List<QryEmployee> author;
    private Integer originId;
    private Integer recentId;
    private Integer tabRelationId;
}
