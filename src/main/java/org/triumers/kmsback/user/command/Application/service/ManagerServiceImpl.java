package org.triumers.kmsback.user.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

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

    private String passwordEncoder(String password) {
        if (password != null) {
            return bCryptPasswordEncoder.encode(password);
        }
        return bCryptPasswordEncoder.encode(DEFAULT_PASSWORD);
    }
}
