package org.triumers.kmsback.tab.command.Application.dto;

import lombok.Data;

@Data
public class CmdJoinEmployeeDTO {
    private Integer id;
    private Boolean isLeader;
    private Integer employeeId;
    private Integer tabId;

    public CmdJoinEmployeeDTO() {
    }

    public CmdJoinEmployeeDTO(Integer id, Boolean isLeader, Integer employeeId, Integer tabId) {
        this.id = id;
        this.isLeader = isLeader;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }

    public CmdJoinEmployeeDTO(Boolean isLeader, Integer employeeId, Integer tabId) {
        this.isLeader = isLeader;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }

    public CmdJoinEmployeeDTO(Integer employeeId, Integer tabId) {
        this.employeeId = employeeId;
        this.tabId = tabId;
    }
}
