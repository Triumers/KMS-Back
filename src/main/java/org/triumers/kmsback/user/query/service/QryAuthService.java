package org.triumers.kmsback.user.query.service;

import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.common.exception.WrongInputValueException;
import org.triumers.kmsback.user.query.dto.QryDocsDTO;
import org.triumers.kmsback.user.query.dto.QryEmployeeDTO;

public interface QryAuthService {
    QryEmployeeDTO myInfo() throws NotLoginException, WrongInputValueException;
    QryDocsDTO findMyPost() throws NotLoginException;
    QryDocsDTO findMyComment() throws NotLoginException;
    QryDocsDTO findMyLike() throws NotLoginException;
    QryDocsDTO findMyFavoritePost() throws NotLoginException;
}
