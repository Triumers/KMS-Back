package org.triumers.kmsback.anonymousboard.command.Application.service;

import org.triumers.kmsback.anonymousboard.command.Application.dto.CmdAnonymousBoardDTO;

public interface CmdAnonymousBoardService {

    CmdAnonymousBoardDTO saveAnonymousBoard(CmdAnonymousBoardDTO cmdAnonymousBoardDTO);

    void deleteAnonymousBoard(int id);
}
