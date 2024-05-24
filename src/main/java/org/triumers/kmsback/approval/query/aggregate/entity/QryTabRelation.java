package org.triumers.kmsback.approval.query.aggregate.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QryTabRelation {
    private int id;
    private boolean isPublic;
    private int tabTopId;
    private int tabBottomId;
    private int teamId;
}
