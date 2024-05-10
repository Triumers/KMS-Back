package org.triumers.kmsback.post.command.domain.aggregate.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_join_employee")
@Data
public class CmdJoinEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IS_LEADER", nullable = false)
    private Boolean isLeader;

    @Column(name = "EMPLOYEE_ID", nullable = false)
    private Integer employeeId;

    @Column(name = "TAB_ID", nullable = false)
    private Integer tabId;

    public CmdJoinEmployee() {
    }

    public CmdJoinEmployee(Boolean isLeader, Integer employeeId, Integer tabId) {
        this.isLeader = isLeader;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }
}
