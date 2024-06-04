package org.triumers.kmsback.user.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdPosition;
import org.triumers.kmsback.user.command.domain.aggregate.entity.CmdRank;
import org.triumers.kmsback.user.command.domain.repository.PositionRepository;
import org.triumers.kmsback.user.command.domain.repository.RankRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CmdDutyServiceTest {

    private final String TEST_POSITION_NAME = "UniqPositionForTest";
    private final String TEST_RANK_NAME = "UniqRankForTest";

    @Autowired
    private CmdDutyService cmdDutyService;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private RankRepository rankRepository;

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

    @DisplayName("직급 추가 테스트")
    @Test
    void addRankTest() {

        // given
        CmdRankDTO rankDTO = new CmdRankDTO();
        rankDTO.setName(TEST_RANK_NAME);

        // when
        int rankId = cmdDutyService.addRank(rankDTO);
        CmdRank rank = rankRepository.findById(rankId);

        // then
        assertNotNull(rank);
        assertEquals(TEST_RANK_NAME, rank.getName());
    }

    @DisplayName("직급명 변경 테스트")
    @Test
    void editRankName() {

        // given
        int rankId = addRankForTest();
        String newName = "새로운 직급명";
        CmdRankDTO rankDTO = new CmdRankDTO(rankId, newName);

        // when
        cmdDutyService.editRankName(rankDTO);

        // then
        assertEquals(newName, rankRepository.findById(rankId).getName());
    }

    @DisplayName("직급 삭제 테스트")
    @Test
    void removeRank() {

        // given
        int rankId = addRankForTest();
        CmdRankDTO rankDTO = new CmdRankDTO(rankId, TEST_RANK_NAME);

        // when
        cmdDutyService.removeRank(rankDTO);

        // then
        assertNull(rankRepository.findById(rankId));
    }

    private int addPositionForTest() {
        CmdPositionDTO positionDTO = new CmdPositionDTO();
        positionDTO.setName(TEST_POSITION_NAME);
        return cmdDutyService.addPosition(positionDTO);
    }

    private int addRankForTest() {
        CmdRankDTO rankDTO = new CmdRankDTO();
        rankDTO.setName(TEST_RANK_NAME);
        return cmdDutyService.addRank(rankDTO);
    }
}