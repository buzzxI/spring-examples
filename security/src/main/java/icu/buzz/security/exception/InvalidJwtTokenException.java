package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class InvalidJwtTokenException extends BaseException {
    public InvalidJwtTokenException(Map<String, Object> map) {
        super(ErrorCode.INVALID_JWT_TOKEN, map);
    }
}
