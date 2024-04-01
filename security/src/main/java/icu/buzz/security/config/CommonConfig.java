package icu.buzz.security.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "buzz.security")
public record CommonConfig(LoginConfig loginConfig) {
    public record LoginConfig(int loginCount, int loginInterval, int lockTime) {
    }
}
