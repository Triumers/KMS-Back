package org.triumers.kmsback.common.exception;

public class AwsS3Exception extends CustomException {
    public AwsS3Exception() {
        super("업로드 실패");
    }
}
