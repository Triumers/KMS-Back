package org.triumers.kmsback.common.exception;

public class NotAuthorizedException extends CustomException {
    public NotAuthorizedException() {
        super("권한이 없습니다.");
    }
}