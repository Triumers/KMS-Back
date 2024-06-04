package org.triumers.kmsback.common.authenticator;

import com.mongodb.client.MongoCollection;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.springframework.transaction.annotation.Transactional;
import org.triumers.kmsback.common.TestUserInfo;
import org.triumers.kmsback.common.auth.authenticator.DB.MongoDBConnection;
import org.triumers.kmsback.common.auth.authenticator.OTPUserService;
import org.triumers.kmsback.common.auth.authenticator.OTPValidator;
import org.triumers.kmsback.common.exception.WrongInputValueException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class OTPValidatorTest {
    private final String EMAIL = TestUserInfo.EMAIL;

    private MongoDBConnection dbConnection;
    private OTPUserService otpUserService;
    private GoogleAuthenticator gAuth;
    private MongoCollection<Document> collection;

    @BeforeAll
    void setUp() {
        dbConnection = new MongoDBConnection();
        otpUserService = new OTPUserService(dbConnection);
        gAuth = new GoogleAuthenticator();
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
    @DisplayName("유효한 OTP 검증 테스트")
    void testValidateOTP_Valid() {
        String secret = otpUserService.createUser(EMAIL);
        int otpCode = gAuth.getTotpPassword(secret);

        boolean isValid = OTPValidator.validateOTP(EMAIL, otpCode);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("유효하지 않은 OTP 검증 테스트")
    void testValidateOTP_Invalid() {
        String secret = otpUserService.createUser(EMAIL);
        int invalidOtpCode = 123456; // 임의의 잘못된 OTP 코드

        boolean isValid = OTPValidator.validateOTP(EMAIL, invalidOtpCode);
        assertFalse(isValid);
    }

    @Test
    @DisplayName("유효하지 않은 인수로 validateInput 테스트")
    void testValidateInput_InvalidArguments() {
        Exception exception = assertThrows(WrongInputValueException.class, () -> {
            OTPValidator.validateInput(new String[]{"onlyonearg"});
        });

        String expectedMessage = "유효하지 않은 입력입니다.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("유효한 인수로 validateInput 테스트")
    void testValidateInput_ValidArguments() {
        try {
            OTPValidator.validateInput(new String[]{"test@example.com", "123456"});
        } catch (WrongInputValueException e) {
            fail("Exception should not be thrown for valid arguments");
        }
    }
}
