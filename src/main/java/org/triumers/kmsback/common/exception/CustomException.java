package org.triumers.kmsback.common.exception;

public class CustomException extends Exception {// Git 충돌 복구용 주석
    public CustomException(String message) {
        super("[ERROR] " + message);
    }
}
