package org.triumers.kmsback.tab.query.dto;

import lombok.Data;

@Data
public class QryTabRelationDTO {

    private Integer id;
    private Boolean isPublic;
    private Integer bottomTabId;
    private Integer topTabId;
    private Integer teamId;

    public QryTabRelationDTO() {
    }

    public QryTabRelationDTO(Boolean isPublic, Integer bottomTabId, Integer topTabId, Integer teamId) {
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
        this.teamId = teamId;
    }

    public QryTabRelationDTO(Boolean isPublic, Integer bottomTabId, Integer topTabId) {
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
    }
}
