package org.triumers.kmsback.user.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.LoggedInUser;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QryEmployeeServiceTest {

    @Autowired
    private QryEmployeeService qryEmployeeService;

    @Autowired
    private LoggedInUser loggedInUser;


    @DisplayName("직원 조회 by Id")
    @Test
    void findEmployeeById() throws WrongInputValueException {

        // given
        int employeeId = userSetting();

        // when
        QryEmployeeDTO result = qryEmployeeService.findEmployeeById(employeeId);

        // then
        assertNotNull(result);
        assertTrue(isEqualEmployee(result));
    }

    @DisplayName("전체 직원 조회")
    @Test
    void findAllEmployee() throws WrongInputValueException {

        // given
        loggedInUser.setting();

        // when
        List<QryEmployeeDTO> result = qryEmployeeService.findAllEmployee();

        // then
        assertNotNull(result);
    }

    @DisplayName("직원 조회 by Email")
    @Test
    void findEmployeeByEmail() throws WrongInputValueException {

        // given
        loggedInUser.setting();

        // when
        QryEmployeeDTO result = qryEmployeeService.findEmployeeByEmail(TestUserInfo.EMAIL);

        // then
        assertNotNull(result);
        assertTrue(isEqualEmployee(result));
    }

    @DisplayName("직원 조회 by name")
    @Test
    void findEmployeeByName() throws WrongInputValueException {

        // given
        loggedInUser.setting();

        // when
        List<QryEmployeeDTO> result = qryEmployeeService.findEmployeeByName(TestUserInfo.NAME);

        // then
        assertNotNull(result);
        for (QryEmployeeDTO employee : result) {
            assertTrue(employee.getName().contains(TestUserInfo.NAME));
        }
    }

    @DisplayName("직원 조회 by TeamId")
    @Test
    void findEmployeeByTeamId() throws WrongInputValueException {

        // given
        loggedInUser.setting();

        // when
        List<QryEmployeeDTO> result = qryEmployeeService.findEmployeeByTeamId(1);

        // then
        assertNotNull(result);
        for (QryEmployeeDTO employee : result) {
            System.out.println("employee = " + employee);
        }
        assertFalse(result.isEmpty());
    }

    private int userSetting() throws WrongInputValueException {
        loggedInUser.setting();
        return qryEmployeeService.findEmployeeByEmail(TestUserInfo.EMAIL).getId();
    }

    private boolean isEqualEmployee(QryEmployeeDTO employee) {
        if (!employee.getEmail().equals(TestUserInfo.EMAIL)) {
            return false;
        }
        if (!employee.getName().equals(TestUserInfo.NAME)) {
            return false;
        }
        return employee.getPhoneNumber().equals(TestUserInfo.PHONE_NUMBER);
    }
}