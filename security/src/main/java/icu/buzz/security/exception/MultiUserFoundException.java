package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class MultiUserFoundException extends BaseException {
    public MultiUserFoundException(Map<String, Object> map) {
        super(ErrorCode.MULTIPLE_USER_FOUND, map);
    }
}
