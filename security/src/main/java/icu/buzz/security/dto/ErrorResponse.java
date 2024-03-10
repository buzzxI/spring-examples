package icu.buzz.security.dto;

import icu.buzz.security.entities.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ErrorResponse {
    private final String path;
    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final Map<String, Object> errorDetails;

    public ErrorResponse(ErrorCode errorCode, String path) {
        this(errorCode, path, null);
    }

    public ErrorResponse(ErrorCode errorCode, String path, Map<String, Object> errorDetails) {
        this.errorCode = errorCode.getCode();
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.path = path;
        this.errorDetails = errorDetails;
    }
}
