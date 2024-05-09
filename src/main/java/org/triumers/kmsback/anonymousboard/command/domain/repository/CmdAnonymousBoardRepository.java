package org.triumers.kmsback.anonymousboard.command.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.triumers.kmsback.anonymousboard.command.domain.aggregate.entity.CmdAnonymousBoard;

@Repository
public interface CmdAnonymousBoardRepository extends JpaRepository<CmdAnonymousBoard, Integer> {

    Page<CmdAnonymousBoard> findAll(Pageable pageable);

    Page<CmdAnonymousBoard> findByTitleContaining(String title, Pageable pageable);

    Page<CmdAnonymousBoard> findByContentContaining(String content, Pageable pageable);

    Page<CmdAnonymousBoard> findByTitleContainingOrContentContaining(String keyword, String keyword1, Pageable pageable);
}
