package icu.buzz.security.constant;

public class SecurityConstant {

    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String[] SYSTEM_WHITELIST = {
            "/auth/login",
            "/auth/sign-up",
    };

    public static final String ERROR_RESOURCE = "/error/**";

    public static final String ADMIN_RESOURCE = "/admin/**";

    public static final String USER_RESOURCE = "/user/**";

    public static final String PUBLIC_RESOURCE = "/public/**";
}
