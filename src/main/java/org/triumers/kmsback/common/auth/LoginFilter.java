package org.triumers.kmsback.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.triumers.kmsback.common.auth.authenticator.service.OTPValidator;
import org.triumers.kmsback.common.util.IpAddressUtil;
import org.triumers.kmsback.user.command.Application.dto.CustomUserDetails;
import org.triumers.kmsback.user.command.domain.aggregate.enums.UserRole;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final String defaultPassword;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${in-house-ip-address}")
    private String defaultIpAddress;

    private final OTPValidator otpValidator;
    private int otpCode = 0;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, String defaultPassword,
                       BCryptPasswordEncoder bCryptPasswordEncoder, OTPValidator otpValidator) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.defaultPassword = defaultPassword;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpValidator = otpValidator;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVO loginVO = new ObjectMapper().readValue(request.getInputStream(), LoginVO.class);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginVO.getEmail(), loginVO.getPassword(), null);

            if (loginVO.getOtpCode() != null) {
                otpCode = Integer.parseInt(loginVO.getOtpCode());
            }

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        String name = customUserDetails.getName();
        String secret = customUserDetails.getSecretCode();

        String clientIpAddress = IpAddressUtil.getClientIp(request);
        
        // 아래 프린트문은 임시용
        System.out.println("clientIpAddress = " + clientIpAddress);
        System.out.println("defaultIpAddress = " + defaultIpAddress);
        System.out.println("clientIpAddress.equals(defaultIpAddress) = " + clientIpAddress.equals(defaultIpAddress));

        // 내부망 또는 개발환경인지 검증하는 로직
        if (!clientIpAddress.equals(defaultIpAddress) && !clientIpAddress.equals("0:0:0:0:0:0:0:1")) {

            System.out.println(username + " 사용자 외부 환경에서 접속");
            System.out.println("접속 IP Address : " + clientIpAddress);
            // 2차 인증
            if (!otpValidator.validateOTP(secret, otpCode)) {
                throw new BadCredentialsException("Invalid OTP");
            }
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        UserRole role = UserRole.valueOf(auth.getAuthority());

        String token = jwtUtil.createJwt(username, role, name);

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("UserRole", role.name());

        // Default 비밀번호인 경우 비밀번호 변경을 유도하기 위해 210 반환
        if (bCryptPasswordEncoder.matches(defaultPassword, customUserDetails.getPassword())) {
            response.setStatus(210);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("비밀번호를 변경해주세요.");
            response.getWriter().flush();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }

    @Getter
    public static class LoginVO {
        private String email;
        private String password;
        private String otpCode;
    }
}
