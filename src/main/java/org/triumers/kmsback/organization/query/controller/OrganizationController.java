package org.triumers.kmsback.organization.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.organization.query.aggregate.vo.*;
import org.triumers.kmsback.organization.query.dto.QryCenterDTO;
import org.triumers.kmsback.organization.query.dto.QryDepartmentDTO;
import org.triumers.kmsback.organization.query.dto.QryTeamDTO;
import org.triumers.kmsback.organization.query.service.QryCenterService;
import org.triumers.kmsback.organization.query.service.QryDepartmentService;
import org.triumers.kmsback.organization.query.service.QryTeamService;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organization/find")
public class OrganizationController {

    private final QryCenterService qryCenterService;
    private final QryDepartmentService qryDepartmentService;
    private final QryTeamService qryTeamService;

    @Autowired
    public OrganizationController(QryCenterService qryCenterService, QryDepartmentService qryDepartmentService, QryTeamService qryTeamService) {
        this.qryCenterService = qryCenterService;
        this.qryDepartmentService = qryDepartmentService;
        this.qryTeamService = qryTeamService;
    }

    @GetMapping("/center/detail/all")
    public ResponseEntity<List<QryCenterVO>> findAllCenterDetail() {

        try {
            List<QryCenterDTO> allCenter = qryCenterService.findAllCenterDetailList();
            List<QryCenterVO> allCenterVO = new ArrayList<>();

            for (QryCenterDTO centerDTO : allCenter) {
                allCenterVO.add(centerDetailToVO(centerDTO));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allCenterVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/center/all")
    public ResponseEntity<List<QryCenterVO>> findAllCenter() {

        try {
            List<QryCenterDTO> allCenter = qryCenterService.findAllCenterList();
            List<QryCenterVO> allCenterVO = new ArrayList<>();

            for (QryCenterDTO centerDTO : allCenter) {
                allCenterVO.add(centerDetailToVO(centerDTO));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allCenterVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/center/id/{id}")
    public ResponseEntity<QryCenterVO> findCenterById(@PathVariable("id") int id) {

        try {
            QryCenterDTO qryCenterDTO = qryCenterService.findCenterById(id);
            return ResponseEntity.status(HttpStatus.OK).body(centerDetailToVO(qryCenterDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/center/detail/id/{id}")
    public ResponseEntity<QryCenterVO> findCenterDetailById(@PathVariable("id") int id) {

        try {
            QryCenterDTO qryCenterDTO = qryCenterService.findCenterDetailById(id);
            return ResponseEntity.status(HttpStatus.OK).body(centerDetailToVO(qryCenterDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("center/name")
    public ResponseEntity<List<QryCenterVO>> findCenterByName(@RequestBody QryRequestSearchNameVO request) {

        try {
            List<QryCenterDTO> allCenter = qryCenterService.findCenterListByName(request.getName());
            List<QryCenterVO> allCenterVO = new ArrayList<>();

            for (QryCenterDTO centerDTO : allCenter) {
                allCenterVO.add(centerDetailToVO(centerDTO));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allCenterVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/department/id/{id}")
    public ResponseEntity<QryDepartmentVO> findDepartmentById(@PathVariable("id") int id) {

        try {
            QryDepartmentDTO qryDepartmentDTO = qryDepartmentService.findDepartmentById(id);
            return ResponseEntity.status(HttpStatus.OK).body(departmentDetailToVO(qryDepartmentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/department/detail/id/{id}")
    public ResponseEntity<QryDepartmentVO> findDepartmentDetailById(@PathVariable("id") int id) {

        try {
            QryDepartmentDTO qryDepartmentDTO = qryDepartmentService.findDepartmentDetailById(id);
            return ResponseEntity.status(HttpStatus.OK).body(departmentDetailToVO(qryDepartmentDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/department/name")
    public ResponseEntity<List<QryDepartmentVO>> findDepartmentByName(@RequestBody QryRequestSearchNameVO request) {

        try {
            List<QryDepartmentDTO> allDepartment = qryDepartmentService.findDepartmentListByName(request.getName());
            List<QryDepartmentVO> allDepartmentVO = new ArrayList<>();

            for (QryDepartmentDTO departmentDTO : allDepartment) {
                allDepartmentVO.add(departmentDetailToVO(departmentDTO));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allDepartmentVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/department/center-id/{centerId}")
    public ResponseEntity<List<QryDepartmentVO>> findDepartmentCenterById(@PathVariable("centerId") int centerId) {

        try {
            List<QryDepartmentDTO> allDepartment = qryDepartmentService.findDepartmentListByCenterId(centerId);
            List<QryDepartmentVO> allDepartmentVO = new ArrayList<>();

            for (QryDepartmentDTO departmentDTO : allDepartment) {
                allDepartmentVO.add(departmentDetailToVO(departmentDTO));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allDepartmentVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/department/detail/center-id/{centerId}")
    public ResponseEntity<List<QryDepartmentVO>> findDepartmentDetailCenterById(@PathVariable("centerId") int centerId) {

        try {
            List<QryDepartmentDTO> allDepartment = qryDepartmentService.findDepartmentDetailListByCenterId(centerId);
            List<QryDepartmentVO> allDepartmentVO = new ArrayList<>();

            for (QryDepartmentDTO departmentDTO : allDepartment) {
                allDepartmentVO.add(departmentDetailToVO(departmentDTO));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allDepartmentVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/team/id/{id}")
    public ResponseEntity<QryTeamVO> findTeamById(@PathVariable("id") int id) {

        try {
            QryTeamDTO team;
            try {
                team = qryTeamService.findQryTeamById(id);
            } catch (WrongInputValueException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body(teamDetailToVO(team));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/team/detail/id/{id}")
    public ResponseEntity<QryTeamVO> findTeamDetailById(@PathVariable("id") int id) {

        try {
            QryTeamDTO team;
            try {
                team = qryTeamService.findQryTeamDetailById(id);
            } catch (WrongInputValueException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body(teamDetailToVO(team));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/team/name")
    public ResponseEntity<List<QryTeamVO>> findTeamByName(@RequestBody QryRequestSearchNameVO request) {

        try {
            List<QryTeamDTO> allTeam;
            try {
                allTeam = qryTeamService.findTeamListByName(request.getName());
            } catch (WrongInputValueException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }


            List<QryTeamVO> allTeamVO = new ArrayList<>();

            for (QryTeamDTO team : allTeam) {
                allTeamVO.add(teamDetailToVO(team));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allTeamVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/team/department-id/{departmentId}")
    public ResponseEntity<List<QryTeamVO>> findTeamByDepartmentId(@PathVariable("departmentId") int departmentId) {

        try {
            List<QryTeamDTO> allTeam;
            try {
                allTeam = qryTeamService.findTeamListByDepartmentId(departmentId);
            } catch (WrongInputValueException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            List<QryTeamVO> allTeamVO = new ArrayList<>();

            for (QryTeamDTO team : allTeam) {
                allTeamVO.add(teamDetailToVO(team));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allTeamVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/team/detail/department-id/{departmentId}")
    public ResponseEntity<List<QryTeamVO>> findTeamDetailByDepartmentId(@PathVariable("departmentId") int departmentId) {

        try {
            List<QryTeamDTO> allTeam = qryTeamService.findTeamDetailListByDepartmentId(departmentId);
            List<QryTeamVO> allTeamVO = new ArrayList<>();

            for (QryTeamDTO team : allTeam) {
                allTeamVO.add(teamDetailToVO(team));
            }

            return ResponseEntity.status(HttpStatus.OK).body(allTeamVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    private QryCenterVO centerDetailToVO(QryCenterDTO qryCenterDTO) {
        QryCenterVO qryCenterVO = new QryCenterVO();
        qryCenterVO.setId(qryCenterDTO.getId());
        qryCenterVO.setName(qryCenterDTO.getName());

        if (qryCenterDTO.getBelongDepartments() != null) {
            List<QryDepartmentVO> belongDepartmentVO = new ArrayList<>();
            for (QryDepartmentDTO departmentDTO : qryCenterDTO.getBelongDepartments()) {
                belongDepartmentVO.add(departmentDetailToVO(departmentDTO));
            }
            qryCenterVO.setBelongDepartments(belongDepartmentVO);
        }

        return qryCenterVO;
    }

    private QryDepartmentVO departmentDetailToVO(QryDepartmentDTO departmentDTO) {
        QryDepartmentVO departmentVO = new QryDepartmentVO();
        departmentVO.setId(departmentDTO.getId());
        departmentVO.setName(departmentDTO.getName());

        List<QryTeamVO> belongTeamVOList = new ArrayList<>();

        if (departmentDTO.getBelongTeams() != null) {
            for (QryTeamDTO teamDTO : departmentDTO.getBelongTeams()) {
                belongTeamVOList.add(teamDetailToVO(teamDTO));
            }
            departmentVO.setBelongTeams(belongTeamVOList);
        }

        return departmentVO;
    }

    private QryTeamVO teamDetailToVO(QryTeamDTO qryTeamDTO) {
        QryTeamVO qryTeamVO = new QryTeamVO();
        qryTeamVO.setId(qryTeamDTO.getId());
        qryTeamVO.setName(qryTeamDTO.getName());

        if (qryTeamDTO.getTeamMembers() != null) {
            List<QryTeamMemberVO> teamMemberList = new ArrayList<>();
            for (QryEmployeeDTO employee : qryTeamDTO.getTeamMembers()) {
                teamMemberList.add(new QryTeamMemberVO(employee.getId(), employee.getName(), employee.getProfileImg()));
            }
            qryTeamVO.setMembers(teamMemberList);
        }

        return qryTeamVO;
    }
}
