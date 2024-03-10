package icu.buzz.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfig(long expireTime, RsaKeys rsaConfig) {
    public record RsaKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
    }
}
