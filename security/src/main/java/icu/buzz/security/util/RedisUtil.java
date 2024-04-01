package icu.buzz.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    // evaluate the value of the default-expire property
    @Value("${spring.data.redis.default-expire}")
    private long defaultExpire;

    private final RedisTemplate<String, Object> template;

    @Autowired
    public RedisUtil(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public Object get(String key) {
//        Object rst = template.opsForValue().get(key);
//        if (rst != null) {
//            // reset the expiration time
//            template.expire(key, defaultExpire, TimeUnit.SECONDS);
//        }
//        return rst;
        return template.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        this.set(key, value, defaultExpire);
    }

    public void set(String key, Object value, long timeout) {
        template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void delete(String key) {
        template.delete(key);
    }
}
