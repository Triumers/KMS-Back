package org.triumers.kmsback.approval.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;

@Repository
public interface CmdRequestApprovalRepository extends JpaRepository<CmdRequestApproval, Integer> {
}
