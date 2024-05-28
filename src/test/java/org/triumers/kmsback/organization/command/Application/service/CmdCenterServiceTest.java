package org.triumers.kmsback.organization.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.organization.command.Application.dto.CmdCenterDTO;
import org.triumers.kmsback.organization.command.domain.repository.CenterRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdCenterServiceTest {

    private final String TEST_CENTER_NAME = "UniqueCenterNameForTest";

    @Autowired
    private CmdCenterService cmdCenterService;

    @Autowired
    private CenterRepository centerRepository;

    @DisplayName("본부 생성 테스트")
    @Test
    void addCenter() {

        // given
        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setName("테스트용 본부");

        // when
        int centerId = cmdCenterService.addCenter(centerDTO);

        // then
        assertEquals(centerDTO.getName(), centerRepository.findById(centerId).getName());
    }

    @DisplayName("본부 이름 수정 테스트")
    @Test
    void editCenterName() {

        // given
        int targetId = addCenterForTest();
        String newName = "새로운 본부 이름";
        CmdCenterDTO centerDTO = new CmdCenterDTO(targetId, newName);

        // when
        cmdCenterService.editCenterName(centerDTO);

        // then
        assertEquals(newName, centerRepository.findById(targetId).getName());
        assertNotEquals(TEST_CENTER_NAME, centerRepository.findById(targetId).getName());
    }

    @DisplayName("본부 삭제 테스트")
    @Test
    void removeCenter() {

        // given
        int targetId = addCenterForTest();
        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setId(targetId);

        // when
        cmdCenterService.removeCenter(centerDTO);

        // then
        assertNull(centerRepository.findById(targetId));
    }

    private int addCenterForTest() {
        CmdCenterDTO centerDTO = new CmdCenterDTO();
        centerDTO.setName(TEST_CENTER_NAME);
        return cmdCenterService.addCenter(centerDTO);
    }
}