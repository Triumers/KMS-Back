package org.triumers.kmsback.common.authenticator;


import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.auth.authenticator.DB.MongoDBConnection;
import org.triumers.kmsback.common.auth.authenticator.OTPUserService;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class OTPUserServiceTest {
    private final String EMAIL = TestUserInfo.EMAIL;

    private MongoDBConnection dbConnection;
    private OTPUserService otpUserService;
    private MongoCollection<Document> collection;

    @BeforeAll
    void setUp() {
        dbConnection = new MongoDBConnection();
        otpUserService = new OTPUserService(dbConnection);
        collection = dbConnection.getCollection();
    }

    @AfterAll
    void tearDown() {
        if (collection != null) {
            collection.drop();
        }
        dbConnection.close();
    }

    @Test
    @DisplayName("사용자 생성 테스트")
    void testCreateUser() {
        String secret = otpUserService.createUser(EMAIL);
        System.out.println("secret = " + secret);
        assertNotNull(secret);

        Document query = new Document("email", EMAIL);
        System.out.println("query = " + query);
        Document user = collection.find(query).first();
        System.out.println("user = " + user);
        assertNotNull(user);
        assertEquals(secret, user.getString("secret"));
    }

    @Test
    @DisplayName("이메일로 시크릿 키 조회 테스트")
    void testGetSecretByEmail() {
        String secret = otpUserService.createUser(EMAIL);

        String retrievedSecret = otpUserService.getSecretByEmail(EMAIL);
        assertEquals(secret, retrievedSecret);
    }

    @Test
    @DisplayName("이메일로 시크릿 키 조회 테스트 - 사용자를 찾을 수 없는 경우")
    void testGetSecretByEmail_NotFound() {
        String secret = otpUserService.getSecretByEmail("nonexistent@example.com");
        assertNull(secret);
    }
}
