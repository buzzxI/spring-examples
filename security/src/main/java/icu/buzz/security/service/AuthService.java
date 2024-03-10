package icu.buzz.security.service;

import icu.buzz.security.dto.LoginRequest;
import icu.buzz.security.entities.User;

public interface AuthService {
    /**
     * authenticate with username and password
     * @param request dto with username and password
     * @return jwt
     */
    String authenticate(LoginRequest request);

    /**
     * user try to sign up
     * @param request sign up info, for now just username and password
     * @return if current username has already present, return false, else return true
     */
    User signUp(LoginRequest request);


    void logout();

}
