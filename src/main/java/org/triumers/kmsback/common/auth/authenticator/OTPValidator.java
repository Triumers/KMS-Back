package org.triumers.kmsback.common.auth.authenticator;


import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.triumers.kmsback.common.auth.authenticator.DB.MongoDBConnection;
import org.triumers.kmsback.common.exception.WrongInputValueException;


public class OTPValidator {

    // OTP 검증 메서드
    public static boolean validateOTP(String email, int userCode) {
        MongoDBConnection dbConnection = new MongoDBConnection();
        OTPUserService userService = new OTPUserService(dbConnection);

        String secret = userService.getSecretByEmail(email); // 이메일 사용하여 시크릿 키 조회
        if (secret == null) {
            dbConnection.close();
            return false;
        }

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorize(secret, userCode);

        dbConnection.close();

        return isCodeValid;
    }

    // 입력 인수 검증하는 메서드
    public static void validateInput(String[] args) throws WrongInputValueException {
        if (args.length < 2) {
            throw new WrongInputValueException();
        }
    }
}
