package org.triumers.kmsback.organization.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.Application.service.CmdCenterService;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryCenterServiceTest {

    @Autowired
    private QryCenterService qryCenterService;

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

    @DisplayName("본부 검색 By 본부 이름")
    @Test
    void findCenterListByName() {

        // given
        List<String> names = new ArrayList<>();
        String target = "UniqWord";

        names.add(target);
        names.add(target + "테스트");
        names.add("테스트" + target);
        names.add("테스트" + target + "테스트");

        for (String name : names) {
            addCenterForTest(name);
        }

        // when
        List<QryCenterDTO> resultList = qryCenterService.findCenterListByName(target);

        // then
        for (QryCenterDTO result : resultList) {
            assertTrue(result.getName().contains(target));
        }
    }

    private int addCenterForTest(String centerName) {
        CmdCenterDTO cmdCenterDTO = new CmdCenterDTO();
        cmdCenterDTO.setName(centerName);
        return cmdCenterService.addCenter(cmdCenterDTO);
    }
}