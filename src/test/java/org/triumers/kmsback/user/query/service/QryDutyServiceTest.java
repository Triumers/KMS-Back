package org.triumers.kmsback.user.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;
import org.triumers.kmsback.user.command.Application.service.CmdDutyService;
import org.triumers.kmsback.user.query.dto.QryPositionDTO;
import org.triumers.kmsback.user.query.dto.QryRankDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryDutyServiceTest {

    private final List<String> names = new ArrayList<>();

    @Autowired
    private QryDutyService qryDutyService;

    @Autowired
    private CmdDutyService cmdDutyService;

    @BeforeEach
    void setUp() {
        for (int i = 1; i < 11; i++) {
            names.add("testName " + i);
        }
    }

    @DisplayName("직책 전체 검색 테스트")
    @Test
    void findAllPositionTest() {

        // given
        addPositionList();

        // when
        List<QryPositionDTO> positionDTOList = qryDutyService.findAllPosition();

        // then
        assertNotNull(positionDTOList);
        assertTrue(positionDTOList.size() >= names.size());
    }

    @DisplayName("직책명 검색")
    @Test
    void findPositionByNameTest() {

        // given
        addPositionList();

        // when
        List<QryPositionDTO> positionDTOListContainTest = qryDutyService.findPositionByName("test");
        List<QryPositionDTO> positionDTOListContainFive = qryDutyService.findPositionByName("5");

        // then
        for (QryPositionDTO positionDTO : positionDTOListContainTest) {
            assertTrue(positionDTO.getName().contains("test"));
        }
        assertTrue(positionDTOListContainTest.size() >= names.size());
        for (QryPositionDTO positionDTO : positionDTOListContainFive) {
            assertTrue(positionDTO.getName().contains("5"));
        }
    }

    @DisplayName("직급 전체 검색 테스트")
    @Test
    void findAllRankTest() {

        // given
        addRankList();

        // when
        List<QryRankDTO> rankDTOList = qryDutyService.findAllRank();

        // then
        assertNotNull(rankDTOList);
        assertTrue(rankDTOList.size() >= names.size());
    }

    @DisplayName("직급명 검색 테스트")
    @Test
    void findRankByNameTest() {

        // given
        addRankList();

        // when
        List<QryRankDTO> rankDTOListContainTest = qryDutyService.findRankByName("test");
        List<QryRankDTO> rankDTOListContainFive = qryDutyService.findRankByName("5");

        // then
        for (QryRankDTO rankDTO : rankDTOListContainTest) {
            assertTrue(rankDTO.getName().contains("test"));
        }
        assertTrue(rankDTOListContainTest.size() >= names.size());
        for (QryRankDTO rankDTO : rankDTOListContainFive) {
            assertTrue(rankDTO.getName().contains("5"));
        }
    }

    private void addPositionList() {
        for (String name : names) {
            CmdPositionDTO cmdPositionDTO = new CmdPositionDTO();
            cmdPositionDTO.setName(name);
            cmdDutyService.addPosition(cmdPositionDTO);
        }
    }

    private void addRankList() {
        for (String name : names) {
            CmdRankDTO cmdRankDTO = new CmdRankDTO();
            cmdRankDTO.setName(name);
            cmdDutyService.addRank(cmdRankDTO);
        }
    }
}