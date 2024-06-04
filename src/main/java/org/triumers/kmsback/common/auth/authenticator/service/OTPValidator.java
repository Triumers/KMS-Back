package org.triumers.kmsback.common.auth.authenticator.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.common.exception.WrongInputValueException;

@Service
public class OTPValidator {

    private final OTPUserService otpUserService;

    @Autowired
    public OTPValidator(OTPUserService otpUserService) {
        this.otpUserService = otpUserService;
    }

    public boolean validateOTP(String email, int userCode) {
        String secret = otpUserService.getSecretByEmail(email);
        if (secret == null) {
            return false;
        }

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        return gAuth.authorize(secret, userCode);
    }

    public void validateInput(String[] args) throws WrongInputValueException {
        if (args.length < 2) {
            throw new WrongInputValueException();
        }
    }
}
