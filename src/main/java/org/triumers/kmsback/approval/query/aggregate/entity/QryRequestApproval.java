package org.triumers.kmsback.approval.query.aggregate.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QryRequestApproval {
    private int id;
    private int approvalOrder;
    private boolean isApproved;
    private boolean isCanceled;
    private int employeeId;
    private int approvalId;
}
