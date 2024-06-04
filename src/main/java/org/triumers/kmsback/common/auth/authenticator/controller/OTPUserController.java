package org.triumers.kmsback.common.auth.authenticator.controller;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.auth.authenticator.service.OTPUserService;
import org.triumers.kmsback.common.exception.NotLoginException;
import org.triumers.kmsback.user.command.Application.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/otp")
public class OTPUserController {

    private final OTPUserService otpUserService;
    private final AuthService authService;

    @Autowired
    public OTPUserController(OTPUserService otpUserService, AuthService authService) {
        this.otpUserService = otpUserService;
        this.authService = authService;
    }

    @GetMapping("/regist")
    public ResponseEntity<String> registOtp() {

        try {
            otpUserService.createUser();
            String email = authService.whoAmI().getEmail();
            String qrCodeBase64 = otpUserService.generateQRCode(email);
            String qrCodeHtml = "<img src=\"data:image/png;base64," + qrCodeBase64 + "\" />";
            return ResponseEntity.status(HttpStatus.OK).body(qrCodeHtml);
        } catch (NotLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("QR 코드 생성 중 오류 발생");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
