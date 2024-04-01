package icu.buzz.security.listener;

import icu.buzz.security.config.CommonConfig;
import icu.buzz.security.entities.JwtUser;
import icu.buzz.security.entities.UserBo;
import icu.buzz.security.util.IpUtil;
import icu.buzz.security.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthenticationEventListener {
    private RedisUtil redisUtil;
    private IpUtil ipUtil;
    private CommonConfig commonConfig;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
    @Autowired
    public void setIpUtil(IpUtil ipUtil) {
        this.ipUtil = ipUtil;
    }
    @Autowired
    public void setCommonConfig(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }

    @EventListener
    public void onSuccess (AuthenticationSuccessEvent event) {
        JwtUser jwtUser = (JwtUser) event.getAuthentication().getPrincipal();
        String userKey =  constructUserKey(jwtUser.getUsername());
        redisUtil.delete(userKey);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        if (event.getException() instanceof BadCredentialsException) {
            String principal = event.getAuthentication().getPrincipal().toString();
            UserBo user = (UserBo) redisUtil.get(principal);
            String userKey = constructUserKey(principal);
            Integer attemptCount = (Integer) redisUtil.get(userKey);
            if (attemptCount != null && attemptCount == commonConfig.loginConfig().loginCount()) {
                // ban user for a period of time
                user.setUnlockedTime(LocalDateTime.now().plusSeconds(commonConfig.loginConfig().lockTime()));
                redisUtil.set(principal, user);
            } else {
                if (attemptCount == null) attemptCount = 1;
                else attemptCount ++;
                redisUtil.set(userKey, attemptCount, commonConfig.loginConfig().loginInterval());
            }
        }
    }

    private String constructUserKey(String username) {
        return username + "_" + ipUtil.getIpAddr();
    }
}
