package org.triumers.kmsback.post.command.domain.aggregate.entity;

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

    @Column(name = "BOTTOM_TAB_ID", nullable = false)
    private Integer bottomTabId;

    @Column(name = "TOP_TAB_ID", nullable = false)
    private Integer topTabId;

    @Column(name = "TEAM_ID")
    private Integer teamId;
}
