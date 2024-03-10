package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class UsernameNotFoundException extends BaseException {
    public UsernameNotFoundException(Map<String, Object> map) {
        super(ErrorCode.USER_NAME_NOT_FOUND, map);
    }
}
