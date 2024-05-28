package org.triumers.kmsback.common.exception;

public class NotLoginException extends CustomException {
    public NotLoginException() {
        super("로그인이 필요한 서비스입니다.");
    }
}