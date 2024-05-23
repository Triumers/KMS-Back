package org.triumers.kmsback.approval.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApprovalType;

@Repository
public interface CmdApprovalTypeRepository extends JpaRepository<CmdApprovalType, Integer> {
}
