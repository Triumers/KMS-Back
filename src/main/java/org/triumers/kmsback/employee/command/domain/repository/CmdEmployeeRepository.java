package org.triumers.kmsback.employee.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.employee.command.domain.aggregate.entity.CmdEmployee;

@Repository
public interface CmdEmployeeRepository extends JpaRepository<CmdEmployee, Integer> {

    CmdEmployee findById(int id);

    CmdEmployee findByEmail(String email);

    CmdEmployee findByName(String name);
}
