package org.triumers.kmsback.user.command.Application.service;

import org.triumers.kmsback.common.exception.NotAuthorizedException;
import org.triumers.kmsback.user.command.Application.dto.ManageUserDTO;

public interface ManagerService {

    void signup(ManageUserDTO userDTO);

    void editUserRole(ManageUserDTO userDTO) throws NotAuthorizedException;

    void initializePassword(ManageUserDTO userDTO) throws NotAuthorizedException;

    void editUserInfo(ManageUserDTO userDTO) throws NotAuthorizedException;
}
