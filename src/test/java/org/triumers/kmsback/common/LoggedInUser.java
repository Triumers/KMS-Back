package org.triumers.kmsback.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.Application.service.ManagerService;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;
import org.triumers.kmsback.user.command.domain.service.CustomUserDetailsService;

@Component
public class LoggedInUser {
    private final String RIGHT_FORMAT_EMAIL = TestUserInfo.EMAIL;
    private final String RIGHT_FORMAT_PASSWORD = TestUserInfo.PASSWORD;
    private final String RIGHT_FORMAT_NAME = TestUserInfo.NAME;
    private final String RIGHT_FORMAT_USER_ROLE = TestUserInfo.USER_ROLE;
    private final String RIGHT_PHONE_NUMBER = TestUserInfo.PHONE_NUMBER;
    private final String HR_MANAGER_EMAIL = TestUserInfo.ADMIN_EMAIL;
    private final String HR_MANAGER_ROLE = TestUserInfo.HR_MANAGER_ROLE;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeRepository employeeRepository;

    // 테스트용 계정 DTO 생성
    private ManageUserDTO createRightAuthDTO() {
        return new ManageUserDTO(RIGHT_FORMAT_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                RIGHT_FORMAT_USER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                1);
    }

    // 테스트용 관리자 계정 DTO 생성
    private ManageUserDTO createHrManagerDTO() {
        return new ManageUserDTO(HR_MANAGER_EMAIL, RIGHT_FORMAT_PASSWORD, RIGHT_FORMAT_NAME, null,
                HR_MANAGER_ROLE, null, null, RIGHT_PHONE_NUMBER, 1, 1,
                1);
    }

    // 테스트용 계정 회원가입
    private void setUser() {
        managerService.signup(createRightAuthDTO());
    }

    // 테스트용 관리자 계정 회원가입
    private void setHrManager() {
        managerService.signup(createHrManagerDTO());
    }

    // 테스트용 계정으로 로그인
    public void setting() {
        setUser();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(employeeRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(RIGHT_FORMAT_EMAIL);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }

    // 테스트용 관리자 계정으로 로그인
    public void settingHrManager() {
        setHrManager();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService(employeeRepository);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(HR_MANAGER_EMAIL);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
    }
}
