package org.triumers.kmsback.approval.command.Application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdRequestApprovalDTO {
    private int approvalOrder;
    private String isApproved;
    private boolean isCanceled;
    private int approvalId;
}
