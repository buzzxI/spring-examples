package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class ExpiredJwtTokenException extends BaseException {

    public ExpiredJwtTokenException(Map<String, Object> map) {
        super(ErrorCode.EXPIRED_JWT_TOKEN, map);
    }
}
