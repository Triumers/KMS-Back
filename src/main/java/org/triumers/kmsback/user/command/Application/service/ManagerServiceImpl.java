package org.triumers.kmsback.user.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

import java.util.Collection;
import java.util.Iterator;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final String DEFAULT_PASSWORD;

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ManagerServiceImpl(@Value("${password}") String password, EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.DEFAULT_PASSWORD = password;
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signup(ManageUserDTO userDTO) {
        Employee employee = new Employee();

        employee.setEmail(userDTO.getEmail());
        employee.setName(userDTO.getName());
        employee.setProfileImg(userDTO.getProfileImg());
        employee.setUserRole(UserRole.valueOf(userDTO.getRole()));
        employee.setStartDate(userDTO.getStartDate());
        employee.setEndDate(userDTO.getEndDate());
        employee.setPhoneNumber(userDTO.getPhoneNumber());
        employee.setTeamId(userDTO.getTeamId());
        employee.setPositionId(userDTO.getPositionId());
        employee.setRankId(userDTO.getRankId());

        employee.setPassword(passwordEncoder(userDTO.getPassword()));

        employeeRepository.save(employee);
    }

    @Override
    public void editUserRole(ManageUserDTO userDTO) throws NotAuthorizedException {
        Employee employee = employeeRepository.findByEmail(userDTO.getEmail());

        // 변경할 대상 권한이 자신의 권한이하인지 검증
        validateRole(employee);

        // 변경할 권한이 자신의 권한을 초과하지 않는지 검증
        if (isNotEnoughRole(userDTO.getRole(), getCurrentRole())) {
            throw new NotAuthorizedException();
        }

        employee.setUserRole(UserRole.valueOf(userDTO.getRole()));

        employeeRepository.save(employee);
    }

    private String passwordEncoder(String password) {
        if (password != null) {
            return bCryptPasswordEncoder.encode(password);
        }
        return bCryptPasswordEncoder.encode(DEFAULT_PASSWORD);
    }

    // 현재 로그인한 회원 권한
    private String getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        return auth.getAuthority();
    }

    private void validateRole(Employee targetUser) throws NotAuthorizedException {

        // 자신을 초과하는 권한인 경우
        if (isNotEnoughRole(targetUser.getUserRole().name(), getCurrentRole())) {
            throw new NotAuthorizedException();
        }
    }

    // 자신의 권한을 초과하는 경우 true 반환
    private boolean isNotEnoughRole(String targetRole, String currentRole) {
        if (targetRole.equals(currentRole)) {
            return false;
        }
        if (targetRole.equals(UserRole.ROLE_HR_MANAGER.name())) {
            return !currentRole.equals(UserRole.ROLE_ADMIN.name());
        }
        if (targetRole.equals(UserRole.ROLE_LEADER.name())) {
            return !currentRole.equals(UserRole.ROLE_ADMIN.name()) &&
                    !currentRole.equals(UserRole.ROLE_HR_MANAGER.name());
        }
        if (targetRole.equals(UserRole.ROLE_NORMAL.name())) {
            return !currentRole.equals(UserRole.ROLE_ADMIN.name()) &&
                    !currentRole.equals(UserRole.ROLE_HR_MANAGER.name()) &&
                    !currentRole.equals(UserRole.ROLE_LEADER.name());
        }
        return true;
    }
}
