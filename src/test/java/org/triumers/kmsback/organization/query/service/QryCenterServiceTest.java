package org.triumers.kmsback.organization.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.service.CmdCenterService;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryCenterServiceTest {

    @Autowired
    private QryCenterServiceImpl qryCenterService;

    @Autowired
    private CmdCenterService cmdCenterService;

    @DisplayName("본부 검색 By 본부Id")
    @Test
    void findCenterByIdTest() {

        // given
        String targetName = "테스트용 본부";
        int targetId = addCenterForTest(targetName);

        // when
        QryCenterDTO qryCenterDTO = qryCenterService.findCenterById(targetId);

        // then
        assertEquals(targetName, qryCenterDTO.getName());
    }

    private int addCenterForTest(String centerName) {
        CmdCenterDTO cmdCenterDTO = new CmdCenterDTO();
        cmdCenterDTO.setName(centerName);
        return cmdCenterService.addCenter(cmdCenterDTO);
    }
}