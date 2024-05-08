package org.triumers.kmsback.post.query.aggregate.entity;

import lombok.Data;


@Data
public class QryTabRelation {

    private Integer id;
    private Boolean isPublic;
    private Integer bottomTabId;
    private Integer topTabId;
    private Integer teamId;
}
