package icu.buzz.security.exception;

import icu.buzz.security.entities.ErrorCode;

import java.util.Map;

public class ServerInternalException extends BaseException {
    public ServerInternalException(Map<String, Object> map) {
        super(ErrorCode.SERVER_INTERNAL_ERROR, map);
    }
}
