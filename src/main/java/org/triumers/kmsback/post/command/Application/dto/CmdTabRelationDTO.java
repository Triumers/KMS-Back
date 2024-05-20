package org.triumers.kmsback.post.command.Application.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CmdTabRelationDTO {

    private Integer id;
    private Boolean isPublic;
    private Integer bottomTabId;
    private Integer topTabId;
    private Integer teamId;

    public CmdTabRelationDTO() {
    }

    public CmdTabRelationDTO(Boolean isPublic, Integer bottomTabId, Integer topTabId, Integer teamId) {
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
        this.teamId = teamId;
    }

    public CmdTabRelationDTO(Boolean isPublic, Integer bottomTabId, Integer topTabId) {
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
    }

    public CmdTabRelationDTO(Integer id, Boolean isPublic, Integer bottomTabId, Integer topTabId, Integer teamId) {
        this.id = id;
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
        this.teamId = teamId;
    }
}
