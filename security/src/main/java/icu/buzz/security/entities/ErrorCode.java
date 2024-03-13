package icu.buzz.security.entities;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NAME_ALREADY_EXIST(1001, HttpStatus.BAD_REQUEST,  "user already exist"),
    USER_NAME_NOT_FOUND(1002, HttpStatus.NOT_FOUND , "user not found"),
    INVALID_JWT_TOKEN(1003, HttpStatus.UNAUTHORIZED, "jwt token illegal"),
    MULTIPLE_USER_FOUND(1004, HttpStatus.INTERNAL_SERVER_ERROR, "multiple user found"),
    PASSWORD_MISMATCH(1005, HttpStatus.BAD_REQUEST, "password mismatch"),
    USER_NOT_AVAILABLE(1006, HttpStatus.FORBIDDEN, "user not available"),
    EXPIRED_JWT_TOKEN(1007, HttpStatus.UNAUTHORIZED, "jwt token expired"),
    ;

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
