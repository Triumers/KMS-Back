package org.triumers.kmsback.common.auth.authenticator;

import org.triumers.kmsback.common.auth.authenticator.DB.MongoDBConnection;


// createUser 메서드는 주어진 이메일로 사용자 생성 및 생성된 사용자의 시크릿 키 반환
public class CreateUser {
    public String createUser(String email) {
        MongoDBConnection dbConnection = new MongoDBConnection();
        OTPUserService userService = new OTPUserService(dbConnection);
        String secret = userService.createUser(email);
        dbConnection.close();
        return secret;
    }
}
