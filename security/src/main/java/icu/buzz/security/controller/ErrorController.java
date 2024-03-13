package icu.buzz.security.controller;

import icu.buzz.security.exception.ExpiredJwtTokenException;
import icu.buzz.security.exception.InvalidJwtTokenException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * this controller is used to throw exception that can be handled by the global exception handler
 * all url should be denied to client by default
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/invalid-jwt-token")
    public void throwInvalidJwtTokenException() {
        throw new InvalidJwtTokenException(Map.of());
    }

    @GetMapping("/expired-jwt-token")
    public void throwExpiredJwtTokenException() {
        throw new ExpiredJwtTokenException(Map.of());
    }
}
