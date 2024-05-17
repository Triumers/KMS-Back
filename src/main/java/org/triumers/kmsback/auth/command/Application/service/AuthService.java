package org.triumers.kmsback.auth.command.Application.service;

import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;
import org.triumers.kmsback.auth.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

public interface AuthService {
    void signup(AuthDTO authDTO) throws WrongInputTypeException;

    void editPassword(PasswordDTO passwordDTO) throws WrongInputTypeException, WrongInputValueException;

    void editMyInfo(AuthDTO authDTO) throws WrongInputTypeException;
}
