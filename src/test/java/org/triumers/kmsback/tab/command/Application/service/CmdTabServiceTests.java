package org.triumers.kmsback.tab.command.Application.service;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.tab.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.tab.command.domain.repository.CmdJoinEmployeeRepository;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CmdTabServiceTests {

    private final CmdTabService cmdTabService;
    private final CmdJoinEmployeeRepository cmdJoinEmployeeRepository;

    private final int EMPLOYEE_ID = 1;
    private final int TAB_RELATION_ID = 999;

    @Autowired
    CmdTabServiceTests(CmdTabService cmdTabService, CmdJoinEmployeeRepository cmdJoinEmployeeRepository) {
        this.cmdTabService = cmdTabService;
        this.cmdJoinEmployeeRepository = cmdJoinEmployeeRepository;
    }

    @Test
    @DisplayName("탭 참여자 등록")
    void addEmployeeTab(){

        CmdJoinEmployeeDTO employee = new CmdJoinEmployeeDTO(false, EMPLOYEE_ID, TAB_RELATION_ID);

        CmdJoinEmployeeDTO savedEmployee = cmdTabService.addEmployeeTab(employee);

        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    @DisplayName("탭 참여자 삭제")
    void deleteEmployeeTab(){

        CmdJoinEmployeeDTO employee = new CmdJoinEmployeeDTO(false, EMPLOYEE_ID, TAB_RELATION_ID);
        cmdTabService.addEmployeeTab(employee);

        CmdJoinEmployeeDTO deletedEmployee = cmdTabService.deleteEmployeeTab(employee);

        assertThat(cmdJoinEmployeeRepository.findById(deletedEmployee.getId())).isEmpty();
    }

    @Test
    @DisplayName("탭 추가")
    void registTab(){
        boolean isPublic = false;
        CmdTabDTO top = new CmdTabDTO("top");
        CmdTabDTO bottom = new CmdTabDTO("bottom");

        CmdTabRelationDTO tabRelation = new CmdTabRelationDTO(isPublic, bottom, top);

        CmdTabRelationDTO registTab = cmdTabService.registTab(tabRelation, EMPLOYEE_ID);

        assertThat(registTab.getId()).isNotNull();
    }
}