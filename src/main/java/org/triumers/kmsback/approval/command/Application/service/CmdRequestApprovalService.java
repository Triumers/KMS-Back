package org.triumers.kmsback.approval.command.Application.service;

import org.triumers.kmsback.approval.command.Application.dto.CmdApprovalRequestDTO;
import org.triumers.kmsback.common.exception.NotLoginException;

public interface CmdRequestApprovalService {
    void createApproval(CmdApprovalRequestDTO requestDto) throws NotLoginException;
    void cancelApproval(int approvalId) throws NotLoginException;
    void approveRequestApproval(int requestApprovalId) throws NotLoginException;
    void rejectRequestApproval(int requestApprovalId) throws NotLoginException;
    void addApproverToRequestApproval(int requestApprovalId, int newApproverId) throws NotLoginException;
}