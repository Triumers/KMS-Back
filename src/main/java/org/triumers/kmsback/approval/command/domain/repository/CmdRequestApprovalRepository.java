package org.triumers.kmsback.approval.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdRequestApproval;

import java.util.List;

@Repository
public interface CmdRequestApprovalRepository extends JpaRepository<CmdRequestApproval, Integer> {
    List<CmdRequestApproval> findByApprovalIdOrderByApprovalOrderAsc(int approvalId);
}
