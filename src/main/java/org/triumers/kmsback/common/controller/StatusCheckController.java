package org.triumers.kmsback.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.triumers.kmsback.common.util.IpAddressUtil;

@RestController
public class StatusCheckController {

    @Value("${in-house-ip-address}")
    private String defaultIpAddress;

    @GetMapping("/health-check")
    public ResponseEntity<String> checkHealthStatus() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body("Server is up and running");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/ip-address-check")
    public ResponseEntity<String> checkIpAddressCheck(HttpServletRequest request) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body("Current Ip Address: " + IpAddressUtil.getClientIp(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/is-in-house")
    public ResponseEntity<String> isInHouse(HttpServletRequest request) {
        try {
            String ipAddress = IpAddressUtil.getClientIp(request);
            String inHouseIpAddress = defaultIpAddress;
            return ResponseEntity.status(HttpStatus.OK).body("current: " + ipAddress + " / in-house: " + inHouseIpAddress + " / is same?: " + ipAddress.equals(inHouseIpAddress));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
