package org.triumers.kmsback.user.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.user.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.user.command.domain.repository.EmployeeRepository;

import java.util.List;

@Service
public class CmdEmployeeServiceImpl implements CmdEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CmdEmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public CmdEmployeeDTO findEmployeeById(int id) {

        return employeeToEmployeeDTO(employeeRepository.findById(id));
    }

    @Override
    public CmdEmployeeDTO findEmployeeByEmail(String email) {
        return employeeToEmployeeDTO(employeeRepository.findByEmail(email));
    }

    @Override
    public List<CmdEmployeeDTO> findEmployeeByName(String name) {
//        return employeeToEmployeeDTO(cmdEmployeeRepository.findByName(name));
        return null;
    }

    private CmdEmployeeDTO employeeToEmployeeDTO(Employee employee) {
        return new CmdEmployeeDTO(employee.getId(), employee.getEmail(), employee.getName(),
                employee.getProfileImg(), employee.getUserRole().name(), employee.getStartDate(), employee.getEndDate(),
                employee.getPhoneNumber(), employee.getTeamId(), employee.getPositionId(), employee.getRankId());
    }
}
