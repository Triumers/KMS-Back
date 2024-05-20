package org.triumers.kmsback.tab.query.dto;

import lombok.Data;

@Data
public class QryJoinEmployeeDTO {
    private Boolean isLeader;
    private Integer employeeId;
    private Integer tabId;

    public QryJoinEmployeeDTO() {
    }

    public QryJoinEmployeeDTO(Boolean isLeader, Integer employeeId, Integer tabId) {
        this.isLeader = isLeader;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }

    public QryJoinEmployeeDTO(Integer employeeId, Integer tabId) {
        this.isLeader = false;
        this.employeeId = employeeId;
        this.tabId = tabId;
    }
}
