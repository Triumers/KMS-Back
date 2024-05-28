package org.triumers.kmsback.anonymousboard.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoardComment;

@Repository
public interface CmdAnonymousBoardCommentRepository extends JpaRepository<CmdAnonymousBoardComment, Integer> {
}
