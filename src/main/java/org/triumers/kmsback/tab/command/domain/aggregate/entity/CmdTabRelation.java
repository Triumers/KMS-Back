package org.triumers.kmsback.tab.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_tab_relation")
@Data
public class CmdTabRelation {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "IS_PUBLIC", nullable = false)
    private Boolean isPublic;

    @Column(name = "BOTTOM_TAB_ID")
    private Integer bottomTabId;

    @Column(name = "TOP_TAB_ID", nullable = false)
    private Integer topTabId;

    @Column(name = "TEAM_ID")
    private Integer teamId;

    public CmdTabRelation() {
    }

    public CmdTabRelation(Boolean isPublic, Integer bottomTabId, Integer topTabId) {
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
    }

    public CmdTabRelation(Boolean isPublic, Integer bottomTabId, Integer topTabId, Integer teamId) {
        this.isPublic = isPublic;
        this.bottomTabId = bottomTabId;
        this.topTabId = topTabId;
        this.teamId = teamId;
    }
}
