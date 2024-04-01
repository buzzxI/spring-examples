package icu.buzz.security.service.impl;

import icu.buzz.security.constant.ExceptionConstant;
import icu.buzz.security.dto.LoginRequest;
import icu.buzz.security.entities.User;
import icu.buzz.security.exception.PasswordMismatchException;
import icu.buzz.security.exception.UserNotAvailableException;
import icu.buzz.security.exception.UsernameAlreadyExistException;
import icu.buzz.security.service.AuthService;
import icu.buzz.security.service.JwtService;
import icu.buzz.security.util.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ContextUtil contextUtil;
    private UserDetailsServiceImpl userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, ContextUtil contextUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.contextUtil = contextUtil;
    }

    @Override
    public String authenticate(LoginRequest request) {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));
            // user may not be available: disabled user (banned), locked (danger), expired (temp user)
            if (!authenticate.isAuthenticated()) {
                throw new UserNotAvailableException(Map.of(ExceptionConstant.USER_NOT_AVAILABLE, request.getUsername()));
            }
            return jwtService.generateToken(authenticate);
    }

    @Override
    public User signUp(LoginRequest request) {
        if (userDetailsService.userPresent(request.getUsername())) {
            throw new UsernameAlreadyExistException(Map.of(ExceptionConstant.USERNAME_ALREADY_EXIST, request.getUsername()));
        }
        return userDetailsService.saveUser(request.getUsername(), passwordEncoder.encode(request.getPassword()));
    }

    @Override
    public void logout() {
        UserDetails currentUser = contextUtil.getCurrentUser();
        userDetailsService.unloadUserByUsername(currentUser.getUsername());
    }
}
