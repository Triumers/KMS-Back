package org.triumers.kmsback.tab.query.aggregate.entity;

import lombok.Data;

@Data
public class QryJoinEmployee {

    private Integer id;
    private Boolean isLeader;
    private Integer employeeId;
    private Integer tabId;
}
