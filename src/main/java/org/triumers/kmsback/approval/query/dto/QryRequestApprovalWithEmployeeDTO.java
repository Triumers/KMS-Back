package org.triumers.kmsback.approval.query.dto;

import lombok.*;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QryRequestApprovalWithEmployeeDTO {
    private QryRequestApprovalInfoDTO approvalInfo;
    private CmdEmployeeDTO requester;
    private CmdEmployeeDTO approver;
}
