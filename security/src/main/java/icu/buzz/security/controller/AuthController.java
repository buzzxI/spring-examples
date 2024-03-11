package icu.buzz.security.controller;

import icu.buzz.security.constant.SecurityConstant;
import icu.buzz.security.dto.LoginRequest;
import icu.buzz.security.entities.User;
import icu.buzz.security.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
        String jwt = authService.authenticate(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstant.TOKEN_HEADER, SecurityConstant.TOKEN_PREFIX + jwt);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    /**
     * when user sign up, an email is needed, but for now, just use LoginRequest to simulate the sign-up process
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody LoginRequest request) {
        User user = authService.signUp(request);
        return ResponseEntity.ok().build();
    }
}
