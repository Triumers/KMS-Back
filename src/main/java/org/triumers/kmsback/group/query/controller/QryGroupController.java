package org.triumers.kmsback.group.query.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;
import org.triumers.kmsback.group.query.service.QryGroupService;

import java.util.List;

@RestController
@RequestMapping("/group")
public class QryGroupController {
    private final QryGroupService qryGroupService;

    public QryGroupController(QryGroupService qryGroupService) {
        this.qryGroupService = qryGroupService;
    }

    /* 설명. 자신이 속한 그룹 목록 조회 */
    @GetMapping("/employee")
    public ResponseEntity<List<QryGroupDTO>> getGroupsByEmployeeId() throws NotLoginException {

        try {
            List<QryGroupDTO> groupInfo = qryGroupService.findGroupByEmployeeId();
            return ResponseEntity.ok(groupInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
