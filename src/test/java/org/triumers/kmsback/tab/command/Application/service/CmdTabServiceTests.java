package org.triumers.kmsback.tab.command.Application.service;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.tab.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdTabRelationDTO;
import org.triumers.kmsback.tab.command.domain.repository.CmdJoinEmployeeRepository;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CmdTabServiceTests {

    private final CmdTabService cmdTabService;
    private final CmdJoinEmployeeRepository cmdJoinEmployeeRepository;
    private final AuthService authService;

    @Autowired
    CmdTabServiceTests(CmdTabService cmdTabService, CmdJoinEmployeeRepository cmdJoinEmployeeRepository, AuthService authService) {
        this.cmdTabService = cmdTabService;
        this.cmdJoinEmployeeRepository = cmdJoinEmployeeRepository;
        this.authService = authService;
    }

    @Test
    @DisplayName("탭 참여자 등록")
    void addEmployeeTab() throws NotLoginException {

        Employee employee = authService.whoAmI();
        CmdTabRelationDTO tab = createTestTab();

        CmdJoinEmployeeDTO joinEmployee = new CmdJoinEmployeeDTO(false, employee.getId(), tab.getId());

        CmdJoinEmployeeDTO savedEmployee = cmdTabService.addEmployeeTab(joinEmployee);

        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    @DisplayName("탭 참여자 삭제")
    void deleteEmployeeTab() throws NotLoginException {

        Employee employee = authService.whoAmI();
        CmdTabRelationDTO tab = createTestTab();

        CmdJoinEmployeeDTO joinEmployee = new CmdJoinEmployeeDTO(false, employee.getId(), tab.getId());
        cmdTabService.addEmployeeTab(joinEmployee);

        CmdJoinEmployeeDTO deletedEmployee = cmdTabService.deleteEmployeeTab(joinEmployee);

        assertThat(cmdJoinEmployeeRepository.findById(deletedEmployee.getId())).isEmpty();
    }

    @Test
    @DisplayName("탭 추가")
    void registTab() throws NotLoginException {

        CmdTabRelationDTO registTab = createTestTab();

        assertThat(registTab.getId()).isNotNull();
    }

    private CmdTabRelationDTO createTestTab() throws NotLoginException {

        CmdTabDTO top = new CmdTabDTO("testTop");
        CmdTabDTO bottom = new CmdTabDTO("testBottom");
        CmdTabRelationDTO tabRelation = new CmdTabRelationDTO(false, bottom, top);

        Employee employee = authService.whoAmI();

        return cmdTabService.registTab(tabRelation, employee.getId());
    }
}