package icu.buzz.security.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis plus config, scan mappers under the package: mapper
 */
@Configuration
@MapperScan("icu.buzz.security.mapper")
public class MybatisConfig {
}
