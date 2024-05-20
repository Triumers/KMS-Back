package org.triumers.kmsback.tab.command.Application.dto;

import lombok.Data;

@Data
public class CmdTabRelationDTO {

    private Integer id;
    private Boolean isPublic;
    private CmdTabDTO bottomTab;
    private CmdTabDTO topTab;
    private Integer teamId;

    public CmdTabRelationDTO() {
    }

    public CmdTabRelationDTO(Integer id, Boolean isPublic, CmdTabDTO bottomTab, CmdTabDTO topTab, Integer teamId) {
        this.id = id;
        this.isPublic = isPublic;
        this.bottomTab = bottomTab;
        this.topTab = topTab;
        this.teamId = teamId;
    }

    public CmdTabRelationDTO(Boolean isPublic, CmdTabDTO bottomTab, CmdTabDTO topTab) {
        this.isPublic = isPublic;
        this.bottomTab = bottomTab;
        this.topTab = topTab;
    }
}
