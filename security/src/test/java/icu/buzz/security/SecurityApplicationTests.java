package icu.buzz.security;

import icu.buzz.security.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Test
	void contextLoads() {
	}

}
