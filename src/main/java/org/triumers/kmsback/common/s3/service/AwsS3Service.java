package org.triumers.kmsback.common.s3.service;

import org.springframework.web.multipart.MultipartFile;
import org.triumers.kmsback.common.exception.AwsS3Exception;

public interface AwsS3Service {

    String upload(MultipartFile file) throws AwsS3Exception;

    String uploadImage(MultipartFile file) throws AwsS3Exception;
}
