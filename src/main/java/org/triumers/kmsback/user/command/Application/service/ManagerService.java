package org.triumers.kmsback.user.command.Application.service;

import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;

public interface ManagerService {

    void signup(ManageUserDTO userDTO);

    void editUserRole(ManageUserDTO userDTO);
}
