package org.triumers.kmsback.approval.query.dto;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class QryRequestApprovalInfoDTO {

    private int requestApprovalId;
    private int approvalOrder;
    private boolean isCanceled;
    private String isApproved;
    private int approverId;
    private int approvalId;

    private String approverName;
    private String requesterName;

    // approval, approval_type
    private String content;
    private LocalDateTime createdAt;
    private int requesterId;
    private int typeId;
    private String type;
}
