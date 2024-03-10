package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class UsernameAlreadyExistException extends BaseException {
    public UsernameAlreadyExistException(Map<String, Object> map) {
        super(ErrorCode.USER_NAME_ALREADY_EXIST, map);
    }
}
