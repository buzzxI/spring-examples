package icu.buzz.security.service;

import icu.buzz.security.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private JwtConfig jwtConfig;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        return Jwts.builder()
                .issuer("self")
                .subject(authentication.getName())
                .claims(Map.of("scope", scope))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(jwtConfig.expireTime(), ChronoUnit.SECONDS)))
                .signWith(jwtConfig.rsaConfig().privateKey())
                .compact();
    }

    public boolean tokenExpire(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
       Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
   }

   private Claims extractAllClaims(String token) {
       return Jwts.parser()
               .verifyWith(jwtConfig.rsaConfig().publicKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
   }
}
