package org.triumers.kmsback.approval.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.approval.command.domain.aggregate.entity.CmdApproval;

import java.util.Optional;

@Repository
public interface CmdApprovalRepository extends JpaRepository<CmdApproval, Integer> {
    @Query("SELECT a FROM CmdApproval a JOIN a.requester r WHERE r.id = :requesterId AND a.id IS NOT NULL")
    Optional<CmdApproval> findByRequesterIdAndIdIsNotNull(@Param("requesterId") int requesterId);
}
