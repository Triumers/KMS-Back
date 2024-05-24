package org.triumers.kmsback.employee.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.employee.command.Application.dto.CmdEmployeeDTO;
import org.triumers.kmsback.employee.command.domain.aggregate.entity.CmdEmployee;
import org.triumers.kmsback.employee.command.domain.repository.CmdEmployeeRepository;

import java.util.List;

@Service
public class CmdEmployeeServiceImpl implements CmdEmployeeService {

    private final CmdEmployeeRepository cmdEmployeeRepository;

    @Autowired
    public CmdEmployeeServiceImpl(CmdEmployeeRepository cmdEmployeeRepository) {
        this.cmdEmployeeRepository = cmdEmployeeRepository;
    }

    @Override
    public CmdEmployeeDTO findEmployeeById(int id) {

        return employeeToEmployeeDTO(cmdEmployeeRepository.findById(id));
    }

    @Override
    public CmdEmployeeDTO findEmployeeByEmail(String email) {
        return employeeToEmployeeDTO(cmdEmployeeRepository.findByEmail(email));
    }

    @Override
    public List<CmdEmployeeDTO> findEmployeeByName(String name) {
//        return employeeToEmployeeDTO(cmdEmployeeRepository.findByName(name));
        return null;
    }

    private CmdEmployeeDTO employeeToEmployeeDTO(CmdEmployee employee) {
        return new CmdEmployeeDTO(employee.getId(), employee.getEmail(), employee.getName(),
                employee.getProfileImg(), employee.getUserRole().name(), employee.getStartDate(), employee.getEndDate(),
                employee.getPhoneNumber(), employee.getTeamId(), employee.getPositionId(), employee.getRankId());
    }
}
