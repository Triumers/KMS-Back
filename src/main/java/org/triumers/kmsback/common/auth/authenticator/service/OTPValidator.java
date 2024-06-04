package org.triumers.kmsback.common.auth.authenticator.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class OTPValidator {

    public boolean validateOTP(String secret, int userCode) {
        if (secret == null) {
            return false;
        }

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(secret, userCode);
    }
}
