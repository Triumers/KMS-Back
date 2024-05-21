package org.triumers.kmsback.approval.command.Application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CmdRequestApprovalDTO {
    private int id;
    private int approvalOrder;
    private boolean isApproved;
    private boolean isCanceled;
    private int employeeId;
    private int approvalId;

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }
}
