package org.triumers.kmsback.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.util.IpAddressUtil;

@RestController
public class StatusCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<String> checkHealthStatus() {

        return ResponseEntity.status(HttpStatus.OK).body("Server is up and running");
    }

    @GetMapping("/ip-address-check")
    public ResponseEntity<String> checkIpAddressCheck(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body("Current Ip Address: " + IpAddressUtil.getClientIp(request));
    }
}
