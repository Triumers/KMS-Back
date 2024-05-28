package org.triumers.kmsback.user.command.Application.service;

import org.triumers.kmsback.user.command.Application.dto.AuthDTO;
import org.triumers.kmsback.user.command.Application.dto.PasswordDTO;
import org.triumers.kmsback.user.command.domain.aggregate.entity.Employee;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputTypeException;
import org.triumers.kmsback.common.exception.WrongInputValueException;

public interface AuthService {

    void editPassword(PasswordDTO passwordDTO) throws WrongInputTypeException, WrongInputValueException, NotLoginException;

    void editMyInfo(AuthDTO authDTO) throws WrongInputTypeException, NotLoginException;

    Employee whoAmI() throws NotLoginException;
}
