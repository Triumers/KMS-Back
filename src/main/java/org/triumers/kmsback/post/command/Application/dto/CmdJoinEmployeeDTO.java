package org.triumers.kmsback.post.command.Application.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class CmdJoinEmployeeDTO {
    private Boolean isLeader;
    private Integer employeeId;
    private Integer tabId;

    public CmdJoinEmployeeDTO() {
    }

    public CmdJoinEmployeeDTO(Boolean isLeader, Integer employeeId, Integer tabId) {
        this.isLeader = isLeader;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }

    public CmdJoinEmployeeDTO(Integer employeeId, Integer tabId) {
        this.isLeader = false;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }
}
