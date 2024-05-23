package org.triumers.kmsback.approval.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalTypeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class CmdApprovalTypeServiceImplTests {

    @Autowired
    private CmdApprovalTypeRepository cmdApprovalTypeRepository;

    @Autowired
    private CmdApprovalTypeService cmdApprovalTypeService;

    @Test
    public void testAddNewApprovalType() {
        String type = "새로운 결재 유형";

        cmdApprovalTypeService.addNewApprovalType(type);

        CmdApprovalType savedApprovalType = cmdApprovalTypeRepository.findAll().get(3);
        assertEquals(type, savedApprovalType.getType());
    }

}