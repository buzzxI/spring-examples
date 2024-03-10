package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class UserNotAvailableException extends BaseException {
    public UserNotAvailableException(Map<String, Object> map) {
        super(ErrorCode.USER_NOT_AVAILABLE, map);
    }
}
