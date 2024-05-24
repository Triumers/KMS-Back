package org.triumers.kmsback.approval.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalTypeRepository;

@Service
public class CmdApprovalTypeServiceImpl implements CmdApprovalTypeService{

    @Autowired
    private CmdApprovalTypeRepository cmdApprovalTypeRepository;

    // 결재 유형 추가
    public void addNewApprovalType(String type) {
        CmdApprovalType approvalType = new CmdApprovalType();
        approvalType.setType(type);
        cmdApprovalTypeRepository.save(approvalType);
    }
}
