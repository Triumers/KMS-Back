package org.triumers.kmsback.user.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.user.command.Application.dto.AuthDTO;
import org.triumers.kmsback.user.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {
    private final String DEFAULT_PASSWORD;

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(@Value("${password}") String password, EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.DEFAULT_PASSWORD = password;
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void signup(AuthDTO authDTO) {
        employeeRepository.save(authDtoToEmployeeMapper(authDTO));
    }

    @Override
    public void editPassword(PasswordDTO passwordDTO) throws WrongInputValueException, NotLoginException {

        Employee employee = whoAmI();

        if (bCryptPasswordEncoder.matches(passwordDTO.getOldPassword(), employee.getPassword())) {

            employee.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));

            employeeRepository.save(employee);

            return;
        }
        throw new WrongInputValueException();
    }

    @Override
    public void editMyInfo(AuthDTO authDTO) throws NotLoginException {

        Employee employee = whoAmI();

        if (authDTO.getName() != null) {
            employee.setName(authDTO.getName());
        }
        if (authDTO.getPhoneNumber() != null) {
            employee.setPhoneNumber(authDTO.getPhoneNumber());
        }
        if (authDTO.getProfileImg() != null) {
            employee.setProfileImg(authDTO.getProfileImg());
        }

        employeeRepository.save(employee);
    }

    // 현재 로그인된 계정 정보 조회
    @Override
    public Employee whoAmI() throws NotLoginException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository.findByEmail(email);

        if (employee == null) {
            throw new NotLoginException();
        }

        return employee;
    }

    private Employee authDtoToEmployeeMapper(AuthDTO authDTO) {
        Employee employee = new Employee();

        employee.setEmail(authDTO.getEmail());
        employee.setName(authDTO.getName());
        employee.setProfileImg(authDTO.getProfileImg());
        employee.setUserRole(UserRole.valueOf(authDTO.getRole()));
        employee.setStartDate(authDTO.getStartDate());
        employee.setEndDate(authDTO.getEndDate());
        employee.setPhoneNumber(authDTO.getPhoneNumber());
        employee.setTeamId(authDTO.getTeamId());
        employee.setPositionId(authDTO.getPositionId());
        employee.setRankId(authDTO.getRankId());

        if (authDTO.getPassword() != null) {
            employee.setPassword(bCryptPasswordEncoder.encode(authDTO.getPassword()));
            return employee;
        }
        employee.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));

        return employee;
    }
}
