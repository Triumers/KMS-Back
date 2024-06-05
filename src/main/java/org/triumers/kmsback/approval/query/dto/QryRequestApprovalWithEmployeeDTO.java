package org.triumers.kmsback.approval.query.dto;

import lombok.*;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class QryRequestApprovalWithEmployeeDTO {
    private QryRequestApprovalInfoDTO approvalInfo;
    private QryEmployeeDTO requester;
    private QryEmployeeDTO approver;
}
