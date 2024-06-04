package org.triumers.kmsback.group.query.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.group.query.dto.QryGroupDTO;
import org.triumers.kmsback.tab.command.Application.dto.CmdJoinEmployeeDTO;
import org.triumers.kmsback.tab.command.Application.service.CmdTabService;
import org.triumers.kmsback.tab.command.domain.repository.CmdJoinEmployeeRepository;
import org.triumers.kmsback.user.command.Application.service.AuthService;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class QryGroupServiceTest {
    @Autowired
    private QryGroupService qryGroupService;
    private QryGroupDTO qryGroupDTO;

    private final CmdTabService cmdTabService;
    private final CmdJoinEmployeeRepository cmdJoinEmployeeRepository;
    private final AuthService authService;

    private final int TAB_RELATION_ID = 999;

    private final LoggedInUser loggedInUser;

    @Autowired
    QryGroupServiceTest(CmdTabService cmdTabService, CmdJoinEmployeeRepository cmdJoinEmployeeRepository, AuthService authService, LoggedInUser loggedInUser) {
        this.cmdTabService = cmdTabService;
        this.cmdJoinEmployeeRepository = cmdJoinEmployeeRepository;
        this.authService = authService;
        this.loggedInUser = loggedInUser;
    }

    @BeforeEach
    public void setUp() throws NotLoginException {
        loggedInUser.setting();

        Employee employee = authService.whoAmI();
        CmdJoinEmployeeDTO employeeTab = new CmdJoinEmployeeDTO(false, employee.getId(), TAB_RELATION_ID);

        cmdTabService.addEmployeeTab(employeeTab);
    }

    @DisplayName("사원번호로 그룹조회")
    @Test
    void findGroupByEmployeeId() throws NotLoginException {

        // When
        List<QryGroupDTO> actualList = qryGroupService.findGroupByEmployeeId();

        // Then
        assertNotNull(actualList);
    }
}