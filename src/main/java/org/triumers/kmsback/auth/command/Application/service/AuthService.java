package org.triumers.kmsback.auth.command.Application.service;

import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.common.exception.WrongInputTypeException;

public interface AuthService {
    void signup(AuthDTO authDTO) throws WrongInputTypeException;
}
