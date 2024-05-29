package org.triumers.kmsback.user.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdPosition;
import org.triumers.kmsback.user.command.domain.repository.PositionRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdDutyServiceTest {

    private final String TEST_POSITION_NAME = "UniqPositionForTest";

    @Autowired
    private CmdDutyService cmdDutyService;

    @Autowired
    private PositionRepository positionRepository;

    @DisplayName("직책 추가 테스트")
    @Test
    void addPosition() {

        // given
        CmdPositionDTO positionDTO = new CmdPositionDTO();
        positionDTO.setName(TEST_POSITION_NAME);

        // when
        CmdPosition position = positionRepository.findById(cmdDutyService.addPosition(positionDTO));

        // then
        assertNotNull(position);
        assertEquals(TEST_POSITION_NAME, position.getName());
    }

    @DisplayName("직책 이름 변경 테스트")
    @Test
    void editPositionName() {

        // given
        int positionId = addPositionForTest();
        String newName = "새로운 직책 이름";
        CmdPositionDTO positionDTO = new CmdPositionDTO();
        positionDTO.setId(positionId);
        positionDTO.setName(newName);

        // when
        cmdDutyService.editPositionName(positionDTO);

        // then
        CmdPosition position = positionRepository.findById(positionId);
        assertEquals(newName, position.getName());
    }

    @DisplayName("직책 삭제 테스트")
    @Test
    void removePosition() {

        // given
        int positionId = addPositionForTest();
        CmdPositionDTO positionDTO = new CmdPositionDTO();
        positionDTO.setId(positionId);

        // when
        cmdDutyService.removePosition(positionDTO);

        // then
        assertNull(positionRepository.findById(positionId));
    }

    private int addPositionForTest() {
        CmdPositionDTO positionDTO = new CmdPositionDTO();
        positionDTO.setName(TEST_POSITION_NAME);
        return cmdDutyService.addPosition(positionDTO);
    }
}