package org.triumers.kmsback.organization.command.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdDepartment;
import org.triumers.kmsback.organization.command.domain.repository.DepartmentRepository;

@Service
public class CmdDepartmentImpl implements CmdDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public CmdDepartmentImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public int addDepartment(CmdDepartmentDTO departmentDTO) {
        return departmentRepository.save(dtoToDepartment(departmentDTO)).getId();
    }

    @Override
    public void editDepartmentName(CmdDepartmentDTO departmentDTO) {
        CmdDepartment department = departmentRepository.findById(departmentDTO.getId());
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
    }

    // 부서의 상위 본부 변경
    @Override
    public void editDepartmentHighLevelCenter(CmdDepartmentDTO departmentDTO) {
        CmdDepartment department = departmentRepository.findById(departmentDTO.getId());
        department.setCenterId(departmentDTO.getCenterId());
        departmentRepository.save(department);
    }

    @Override
    public void removeDepartmentById(CmdDepartmentDTO departmentDTO) {
        CmdDepartment department = departmentRepository.findById(departmentDTO.getId());
        departmentRepository.delete(department);
    }

    private CmdDepartment dtoToDepartment(CmdDepartmentDTO dto) {
        return new CmdDepartment(dto.getId(), dto.getName(), dto.getCenterId());
    }
}
