package org.triumers.kmsback.auth.command.Application.service;

import org.triumers.kmsback.auth.command.Application.dto.AuthDTO;

public interface AuthService {
    void signup(AuthDTO authDTO);
}
