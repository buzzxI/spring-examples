package icu.buzz.redis.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("icu.buzz.redis.mapper")
public class MybatisConfig {
}
