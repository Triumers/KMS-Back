package org.triumers.kmsback.approval.command.Application.service;

import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;

public interface CmdRequestApprovalService {

    void createApproval(CmdApprovalRequestDTO requestDto, int requesterId);
    void cancelApproval(int requesterId, int approvalId);
}
