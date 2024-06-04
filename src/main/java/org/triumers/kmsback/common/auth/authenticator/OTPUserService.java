package org.triumers.kmsback.common.auth.authenticator;

import com.mongodb.client.MongoCollection;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.bson.Document;
import org.triumers.kmsback.common.auth.authenticator.DB.MongoDBConnection;

public class OTPUserService {
    private MongoCollection<Document> collection;

    public OTPUserService(MongoDBConnection dbConnection) {
        this.collection = dbConnection.getCollection();
    }

    // 사용자 생성 메서드
    public String createUser(String email) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        String secret = key.getKey();

        // 사용자 정보를 포함한 Document 객체 생성
        Document user = new Document("email", email)
                .append("secret", secret);
        collection.insertOne(user); // 컬렉션에 사용자 정보 삽입

        return secret;
    }

    // 이메일로 시크릿 키 조회하는 메서드
    public String getSecretByEmail(String email) {
        Document query = new Document("email", email);
        Document user = collection.find(query).first();
        if (user != null) {
            return user.getString("secret");
        }
        return null;
    }
}
