package icu.buzz.security.filter;

import icu.buzz.security.constant.SecurityConstant;
import icu.buzz.security.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtService jwtService;
    private UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConstant.TOKEN_HEADER);

        if (token == null || !token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }

        // check jwt token
        token = token.replace(SecurityConstant.TOKEN_PREFIX, "");
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (!jwtService.tokenExpire(token)) {
                    String username = jwtService.extractUsername(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // current context need an authentication token, just new an instance
                    // set UserDetails as principal, token as credentials
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JwtException e) {
                // dispatch the request, so that global exception handler can handle this exception
                request.getRequestDispatcher("/error/invalid-jwt-token").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }
}
