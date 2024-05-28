package org.triumers.kmsback.common.util;

public class RegularExpression {
    public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,12}$";
    public static final String NAME = "^[가-힣a-zA-Z]+$";
    public static final String PHONE_NUMBER = "^010-?([0-9]{3,4})-?([0-9]{4})$";
}
