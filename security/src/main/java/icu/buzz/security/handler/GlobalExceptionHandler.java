package icu.buzz.security.handler;

import icu.buzz.security.dto.ErrorResponse;
import icu.buzz.security.exception.*;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * handle exception: current username has already in use
     */
    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExist(UsernameAlreadyExistException e, HttpServletRequest request) {
        return simpleErrorHandling(e, request.getRequestURI());
    }

    /**
     * handle exception: username not found
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(UsernameNotFoundException e, HttpServletRequest request) {
        return simpleErrorHandling(e, request.getRequestURI());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatch(PasswordMismatchException e, HttpServletRequest request) {
        return simpleErrorHandling(e, request.getRequestURI());
    }

    /**
     * handle exception: multiple users found (internal exception)
     */
    @ExceptionHandler(MultiUserFoundException.class)
    public ResponseEntity<ErrorResponse> handleMultiUserFound(MultiUserFoundException e, HttpServletRequest request) {
        return simpleErrorHandling(e, request.getRequestURI());
    }


    /**
     * handle exception: user not available (disabled, locked, expired)
     */
    @ExceptionHandler(UserNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleUserNotAvailable(UserNotAvailableException e, HttpServletRequest request) {
        return simpleErrorHandling(e, request.getRequestURI());
    }

    /**
     * handle exception: invalid jwt token
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException e, HttpServletRequest request) {
        return simpleErrorHandling(new InvalidJwtTokenException(Map.of("invalid jwt token", e.getMessage())), request.getRequestURI());
    }

    /**
     * simple error handling
     * construct an error response from exception
     */
    private ResponseEntity<ErrorResponse> simpleErrorHandling(BaseException e, String uri) {
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), uri, e.getMap());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }
}
