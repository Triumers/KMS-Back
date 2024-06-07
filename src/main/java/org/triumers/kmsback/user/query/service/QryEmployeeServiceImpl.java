package org.triumers.kmsback.user.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.organization.query.service.QryTeamService;
import org.triumers.kmsback.user.query.aggregate.entity.QryEmployee;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;
import org.triumers.kmsback.user.query.repository.EmployeeMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QryEmployeeServiceImpl implements QryEmployeeService {

    private final EmployeeMapper employeeMapper;
    private final QryTeamService qryTeamService;
    private final QryDutyService qryDutyService;

    @Autowired
    public QryEmployeeServiceImpl(EmployeeMapper employeeMapper,
                                  @Lazy QryTeamService qryTeamService,
                                  QryDutyService qryDutyService) {
        this.employeeMapper = employeeMapper;
        this.qryTeamService = qryTeamService;
        this.qryDutyService = qryDutyService;
    }

    @Override
    public QryEmployeeDTO findEmployeeById(int id) throws WrongInputValueException {
        QryEmployee employee = employeeMapper.findById(id);

        return employeeToDTO(employee);
    }

    @Override
    public List<QryEmployeeDTO> findAllEmployee() throws WrongInputValueException {

        List<QryEmployee> employeeList = employeeMapper.findAllEmployee();
        List<QryEmployeeDTO> employeeDTOList = new ArrayList<>();

        for (QryEmployee employee : employeeList) {
            employeeDTOList.add(employeeToDTO(employee));
        }

        return employeeDTOList;
    }

    @Override
    public QryEmployeeDTO findEmployeeByEmail(String email) throws WrongInputValueException {
        return employeeToDTO(employeeMapper.findByEmail(email));
    }

    @Override
    public List<QryEmployeeDTO> findEmployeeByName(String name) throws WrongInputValueException {

        List<QryEmployee> employeeList = employeeMapper.findByName(name);
        List<QryEmployeeDTO> employeeDTOList = new ArrayList<>();

        for (QryEmployee employee : employeeList) {
            employeeDTOList.add(employeeToDTO(employee));
        }

        return employeeDTOList;
    }

    @Override
    public List<QryEmployeeDTO> findEmployeeByTeamId(int teamId) throws WrongInputValueException {

        List<QryEmployee> employeeList = employeeMapper.findByTeamId(teamId);
        List<QryEmployeeDTO> employeeDTOList = new ArrayList<>();

        for (QryEmployee employee : employeeList) {
            employeeDTOList.add(employeeToDTO(employee));
        }

        return employeeDTOList;
    }

    @Override
    public List<QryEmployeeDTO> findSimpleInfoByTeamId(int teamId) {

        List<QryEmployee> employeeList = employeeMapper.findSimpleInfoByTeamId(teamId);
        List<QryEmployeeDTO> employeeDTOList = new ArrayList<>();

        for (QryEmployee employee : employeeList) {
            QryEmployeeDTO employeeDTO = new QryEmployeeDTO();
            employeeDTO.setId(employee.getId());
            employeeDTO.setName(employee.getName());
            employeeDTO.setProfileImg(employee.getProfileImg());

            employeeDTOList.add(employeeDTO);
        }

        return employeeDTOList;
    }

    @Override
    public QryEmployeeDTO findByIdIncludeEnd(int id) throws WrongInputValueException {
        QryEmployee employee = employeeMapper.findByIdIncludeEnd(id);

        return employeeToDTO(employee);
    }

    private QryEmployeeDTO employeeToDTO(QryEmployee employee) throws WrongInputValueException {

        Map<String, String> team = new HashMap<>();
        team.put("id", String.valueOf(employee.getTeamId()));
        team.put("name", qryTeamService.findQryTeamById(employee.getTeamId()).getName());

        Map<String, String> position = new HashMap<>();
        position.put("id", String.valueOf(employee.getPositionId()));
        position.put("name", qryDutyService.findPositionById(employee.getPositionId()).getName());

        Map<String, String> rank = new HashMap<>();
        rank.put("id", String.valueOf(employee.getRankId()));
        rank.put("name", qryDutyService.findRankById(employee.getRankId()).getName());

        return new QryEmployeeDTO(employee.getId(), employee.getEmail(), employee.getName(), employee.getProfileImg(),
                                  employee.getStartDate(), employee.getEndDate(), employee.getPhoneNumber(),
                                  team, position, rank);
    }
}
