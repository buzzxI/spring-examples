package icu.buzz.redis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    @Qualifier("String2StringTemplate")
    private RedisTemplate<String, String> template;

    @PostMapping("/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) {
        template.opsForValue().set(key, value);
        return "success";
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key) {
        return template.opsForValue().get(key);
    }
}
