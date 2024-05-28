package org.triumers.kmsback.common.s3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.triumers.kmsback.common.exception.AwsS3Exception;
import org.triumers.kmsback.common.s3.service.AwsS3Service;

@RestController
@RequestMapping("/s3")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @Autowired
    public AwsS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            String fileLoc = awsS3Service.upload(file);
            return ResponseEntity.status(HttpStatus.OK).body(fileLoc);
        } catch (AwsS3Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
