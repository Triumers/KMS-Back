package org.triumers.kmsback.approval.command.Application.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;
import org.triumers.kmsback.approval.command.domain.repository.CmdApprovalTypeRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdApprovalTypeServiceImplTests {

    @Autowired
    private CmdApprovalTypeRepository cmdApprovalTypeRepository;

    @Autowired
    private CmdApprovalTypeService cmdApprovalTypeService;

    @BeforeEach
    public void setup() {
        // 기존 데이터 삭제
        cmdApprovalTypeRepository.deleteAll();

        // 테스트 데이터 생성
        CmdApprovalType approvalType1 = new CmdApprovalType();
        approvalType1.setType("결재 유형 1");

        CmdApprovalType approvalType2 = new CmdApprovalType();
        approvalType2.setType("결재 유형 2");

        CmdApprovalType approvalType3 = new CmdApprovalType();
        approvalType3.setType("결재 유형 3");

        CmdApprovalType approvalType4 = new CmdApprovalType();
        approvalType4.setType("결재 유형 4");

        CmdApprovalType approvalType5 = new CmdApprovalType();
        approvalType5.setType("결재 유형 5");

        // 테스트 데이터 DB에 저장
        cmdApprovalTypeRepository.save(approvalType1);
        cmdApprovalTypeRepository.save(approvalType2);
        cmdApprovalTypeRepository.save(approvalType3);
        cmdApprovalTypeRepository.save(approvalType4);
        cmdApprovalTypeRepository.save(approvalType5);
    }

//    @Test
//    public void addNewApprovalType() {
//        String type = "새로운 결재 유형";
//
//        cmdApprovalTypeService.addNewApprovalType(type);
//
//        CmdApprovalType savedApprovalType = cmdApprovalTypeRepository.findAll().get(5);
//        assertEquals(type, savedApprovalType.getType());
//    }
}