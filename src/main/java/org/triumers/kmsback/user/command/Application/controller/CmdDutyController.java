package org.triumers.kmsback.user.command.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.user.command.Application.dto.CmdPositionDTO;
import org.triumers.kmsback.user.command.Application.dto.CmdRankDTO;
import org.triumers.kmsback.user.command.Application.service.CmdDutyService;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdRequestDutyVO;
import org.triumers.kmsback.user.command.domain.aggregate.vo.CmdResponseMessageVO;

@RestController
@RequestMapping("/manager/duty")
public class CmdDutyController {

    private final CmdDutyService cmdDutyService;

    @Autowired
    public CmdDutyController(CmdDutyService cmdDutyService) {
        this.cmdDutyService = cmdDutyService;
    }

    @PostMapping("/add/position")
    public ResponseEntity<CmdResponseMessageVO> addPosition(@RequestBody CmdRequestDutyVO request) {

        CmdPositionDTO positionDTO = new CmdPositionDTO(request.getPositionId(), request.getPositionName());

        cmdDutyService.addPosition(positionDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO(positionDTO.getName() + " 직책 추가 성공"));
    }

    @PutMapping("/edit/position")
    public ResponseEntity<CmdResponseMessageVO> editPosition(@RequestBody CmdRequestDutyVO request) {

        CmdPositionDTO positionDTO = new CmdPositionDTO(request.getPositionId(), request.getPositionName());
        cmdDutyService.editPositionName(positionDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO(request.getPositionName() + " 직책 " + positionDTO.getName() + " 직책으로 수정 성공"));
    }

    @DeleteMapping("/remove/position")
    public ResponseEntity<CmdResponseMessageVO> removePosition(@RequestBody CmdRequestDutyVO request) {

        CmdPositionDTO positionDTO = new CmdPositionDTO(request.getPositionId(), request.getPositionName());

        cmdDutyService.removePosition(positionDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO("직책 삭제 성공"));
    }

    @PostMapping("/add/rank")
    public ResponseEntity<CmdResponseMessageVO> addRank(@RequestBody CmdRequestDutyVO request) {

        CmdRankDTO rankDTO = new CmdRankDTO(request.getRankId(), request.getRankName());

        cmdDutyService.addRank(rankDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO(request.getRankName() + " 직급 추가 성공"));
    }

    @PutMapping("/edit/rank")
    public ResponseEntity<CmdResponseMessageVO> editRank(@RequestBody CmdRequestDutyVO request) {

        CmdRankDTO rankDTO = new CmdRankDTO(request.getRankId(), request.getRankName());

        cmdDutyService.editRankName(rankDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO(rankDTO.getName() + " 직급으로 수정 성공"));
    }

    @DeleteMapping("/remove/rank")
    public ResponseEntity<CmdResponseMessageVO> removeRank(@RequestBody CmdRequestDutyVO request) {

        CmdRankDTO rankDTO = new CmdRankDTO(request.getRankId(), request.getRankName());

        cmdDutyService.removeRank(rankDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new CmdResponseMessageVO("직급 삭제 성공"));
    }
}
