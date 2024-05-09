package org.triumers.kmsback.post.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.post.command.domain.aggregate.entity.CmdJoinEmployee;

@Repository
public interface CmdJoinEmployeeRepository extends JpaRepository<CmdJoinEmployee, Integer> {
    CmdJoinEmployee findByEmployeeIdAndTabId(Integer employeeId, Integer tabId);
}
