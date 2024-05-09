package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;

public interface CmdAnonymousBoardService {

    Page<CmdAnonymousBoardDTO> findAllAnonymousBoard(Pageable pageable);

}
