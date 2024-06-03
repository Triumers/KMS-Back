package org.triumers.kmsback.user.query.service;

import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.query.dto.QryDocsDTO;

public interface QryAuthService {
    QryDocsDTO findMyPost() throws NotLoginException;
    QryDocsDTO findMyComment();
    QryDocsDTO findMyLike() throws NotLoginException;
    QryDocsDTO findMyFavoritePost() throws NotLoginException;
}
