package org.triumers.kmsback.common.exception;

public class WrongInputTypeException extends CustomException {
    public WrongInputTypeException() {
        super("유효하지 않은 입력입니다.");
    }
}
