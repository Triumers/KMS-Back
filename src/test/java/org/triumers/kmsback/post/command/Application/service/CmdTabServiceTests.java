package org.triumers.kmsback.post.command.Application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.triumers.kmsback.post.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.post.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdTabRelation;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CmdTabServiceTests {

    private final CmdTabService cmdTabService;

    @Autowired
    CmdTabServiceTests(CmdTabService cmdTabService) {
        this.cmdTabService = cmdTabService;
    }

    @Test
    @DisplayName("탭 참여자 등록")
    void addEmployeeTab(){

        int tabRelationId = 2;
        int employeeId = 1;
        CmdJoinEmployeeDTO employee = new CmdJoinEmployeeDTO(employeeId, tabRelationId);

        CmdJoinEmployee savedEmployee = cmdTabService.addEmployeeTab(employee);

        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    @DisplayName("탭 참여자 삭제")
    void deleteEmployeeTab(){

        int tabRelationId = 1;
        int employeeId = 1;
        CmdJoinEmployeeDTO employee = new CmdJoinEmployeeDTO(employeeId, tabRelationId);
        cmdTabService.addEmployeeTab(employee);

        CmdJoinEmployee deletedEmployee = cmdTabService.deleteEmployeeTab(employee);

        assertThat(deletedEmployee.getId()).isNotNull();
    }

    @Test
    @DisplayName("탭 추가")
    void registTab(){

        int employeeId = 5;

        int topId = 1;
        int bottomId = 1;
        boolean isPublic = false;
        CmdTabRelationDTO tabRelation = new CmdTabRelationDTO(isPublic, bottomId, topId);

        CmdTabRelation registTab = cmdTabService.registTab(tabRelation, employeeId);

        assertThat(registTab.getId()).isNotNull();
    }
}