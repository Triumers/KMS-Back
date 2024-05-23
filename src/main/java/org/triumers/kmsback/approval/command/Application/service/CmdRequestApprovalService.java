package org.triumers.kmsback.approval.command.Application.service;

import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;

public interface CmdRequestApprovalService {

    void createApproval(CmdApprovalRequestDTO requestDto, int requesterId);
    void cancelApproval(int requesterId, int approvalId);
    void approveRequestApproval(int approverId, int requestApprovalId);
    void rejectRequestApproval(int approverId, int requestApprovalId);
    void addApproverToRequestApproval(int approverId, int requestApprovalId, int newApproverId);
}
