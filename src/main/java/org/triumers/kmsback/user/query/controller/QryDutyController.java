package org.triumers.kmsback.user.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.user.query.aggregate.vo.QryPositionVO;
import org.triumers.kmsback.user.query.aggregate.vo.QryRankVO;
import org.triumers.kmsback.user.query.aggregate.vo.QryResponsePositionListVO;
import org.triumers.kmsback.user.query.aggregate.vo.QryResponseRankListVO;
import org.triumers.kmsback.user.query.dto.QryPositionDTO;
import org.triumers.kmsback.user.query.dto.QryRankDTO;
import org.triumers.kmsback.user.query.service.QryDutyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/duty/find")
public class QryDutyController {

    private final QryDutyService qryDutyService;

    @Autowired
    public QryDutyController(QryDutyService qryDutyService) {
        this.qryDutyService = qryDutyService;
    }

    @GetMapping("/position/all")
    public ResponseEntity<QryResponsePositionListVO> findAllPosition() {

        try {
            List<QryPositionDTO> positionDTOList = qryDutyService.findAllPosition();
            List<QryPositionVO> positionVOList = new ArrayList<>();

            for (QryPositionDTO positionDTO : positionDTOList) {
                positionVOList.add(new QryPositionVO(positionDTO.getId(), positionDTO.getName()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new QryResponsePositionListVO(positionVOList));
        } catch (Exception e) {
            System.err.println("findAllPosition: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/position/{positionName}")
    public ResponseEntity<QryResponsePositionListVO> findPositionByName(@PathVariable String positionName) {

        try {
            List<QryPositionDTO> positionDTOList = qryDutyService.findPositionByName(positionName);
            List<QryPositionVO> positionVOList = new ArrayList<>();

            for (QryPositionDTO positionDTO : positionDTOList) {
                positionVOList.add(new QryPositionVO(positionDTO.getId(), positionDTO.getName()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new QryResponsePositionListVO(positionVOList));
        } catch (Exception e) {
            System.err.println("findPositionByName: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/rank/all")
    public ResponseEntity<QryResponseRankListVO> findAllRank() {

        try {
            List<QryRankDTO> rankDTOList = qryDutyService.findAllRank();
            List<QryRankVO> rankVOList = new ArrayList<>();

            for (QryRankDTO rankDTO : rankDTOList) {
                rankVOList.add(new QryRankVO(rankDTO.getId(), rankDTO.getName()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseRankListVO(rankVOList));
        } catch (Exception e) {
            System.err.println("findAllRank: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/rank/{rankName}")
    public ResponseEntity<QryResponseRankListVO> findRankByName(@PathVariable String rankName) {

        try {
            List<QryRankDTO> rankDTOList = qryDutyService.findRankByName(rankName);
            List<QryRankVO> rankVOList = new ArrayList<>();

            for (QryRankDTO rankDTO : rankDTOList) {
                rankVOList.add(new QryRankVO(rankDTO.getId(), rankDTO.getName()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new QryResponseRankListVO(rankVOList));
        } catch (Exception e) {
            System.err.println("findRankByName: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
