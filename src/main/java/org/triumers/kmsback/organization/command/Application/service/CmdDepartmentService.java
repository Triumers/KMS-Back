package org.triumers.kmsback.organization.command.Application.service;

import org.triumers.kmsback.organization.command.Application.dto.CmdDepartmentDTO;

public interface CmdDepartmentService {

    int addDepartment(CmdDepartmentDTO departmentDTO);

    void editDepartmentName(CmdDepartmentDTO departmentDTO);

    void editDepartmentHighLevelCenter(CmdDepartmentDTO departmentDTO);

    void removeDepartmentById(CmdDepartmentDTO departmentDTO);
}
