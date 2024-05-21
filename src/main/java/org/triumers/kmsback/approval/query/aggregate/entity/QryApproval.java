package org.triumers.kmsback.approval.query.aggregate.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QryApproval {
    private int id;
    private String content;
    private LocalDateTime createdAt;
    private int typeId;
    private int employeeId;
    private int tabId;
}
