package org.triumers.kmsback.common.exception;

public class WrongInputValueException extends CustomException {
    public WrongInputValueException() {
        super("유효하지 않은 입력입니다.");
    }
}
