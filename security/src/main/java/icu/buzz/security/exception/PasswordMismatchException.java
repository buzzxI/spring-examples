package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class PasswordMismatchException extends BaseException {
    public PasswordMismatchException(Map<String, Object> map) {
        super(ErrorCode.PASSWORD_MISMATCH, map);
    }
}
