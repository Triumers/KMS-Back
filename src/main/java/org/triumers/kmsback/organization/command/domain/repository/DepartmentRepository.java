package org.triumers.kmsback.organization.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.organization.command.domain.aggregate.entity.CmdDepartment;

@Repository
public interface DepartmentRepository extends JpaRepository<CmdDepartment, Integer> {

    CmdDepartment findById(int id);
}
