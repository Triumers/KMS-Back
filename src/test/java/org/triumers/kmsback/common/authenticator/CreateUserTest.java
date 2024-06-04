package org.triumers.kmsback.common.authenticator;

import org.bson.Document;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.auth.authenticator.CreateUser;
import org.triumers.kmsback.common.auth.authenticator.DB.MongoDBConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class CreateUserTest {

    private MongoDBConnection dbConnection;
    private CreateUser createUser;

    @BeforeAll
    void setUp() {
        dbConnection = new MongoDBConnection();
        createUser = new CreateUser();
    }

    @AfterAll
    void tearDown() {
        dbConnection.getCollection().drop();
        dbConnection.close();
    }

    @Test
    @DisplayName("사용자 생성 및 시크릿 키 반환 테스트")
    void testCreateUser() {
        String email = "test@example.com";
        String secret = createUser.createUser(email);
        assertNotNull(secret);

        Document query = new Document("email", email);
        Document user = dbConnection.getCollection().find(query).first();
        assertNotNull(user);
        assertEquals(secret, user.getString("secret"));
    }
}
