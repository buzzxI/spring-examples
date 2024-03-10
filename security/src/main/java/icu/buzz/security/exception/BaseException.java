package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;
import lombok.Getter;

import java.util.Map;

@Getter
public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    private final transient Map<String, Object> map;

    public BaseException(ErrorCode errorCode, Map<String, Object> map) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.map = map;
    }
}
