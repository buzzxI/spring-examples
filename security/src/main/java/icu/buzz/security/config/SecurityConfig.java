package icu.buzz.security.config;

import icu.buzz.security.constant.SecurityConstant;
import icu.buzz.security.entities.Role;
import icu.buzz.security.filter.JwtAuthenticationFilter;
import icu.buzz.security.handler.CustomAccessDeniedHandler;
import icu.buzz.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// this configuration will enable default SecurityFilterChain
//@EnableWebSecurity
@Configuration
public class SecurityConfig {
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private CustomAccessDeniedHandler customAccessDeniedHandler;

	@Autowired
	public void setJwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Autowired
	public void setCustomAuthenticationEntryPoint(CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
		this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
	}

	@Autowired
	public void setCustomAccessDeniedHandler(CustomAccessDeniedHandler customAccessDeniedHandler) {
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
		http
				// any request must be authenticated
				.authorizeHttpRequests((authorize) -> authorize
						// permit request to `login` and `sign up`
						.requestMatchers(HttpMethod.POST, SecurityConstant.SYSTEM_WHITELIST).permitAll()
						// only users with admin can access `/admin/**`
						.requestMatchers(SecurityConstant.ADMIN_RESOURCE).hasRole(Role.ADMIN.name())
						// all users signing up can access `/user/**`
						.requestMatchers(SecurityConstant.USER_RESOURCE).hasAnyRole(Role.USER.name(), Role.ADMIN.name())
						// anonymous users can access `/public/**`
						.requestMatchers(SecurityConstant.PUBLIC_RESOURCE).permitAll()
						.anyRequest().authenticated()
				)
				// in a jwt based system, csrf protection is not needed -> (two stage token is recommended -> refresh token and in-memory jwt token)
				.csrf(AbstractHttpConfigurer::disable)
				// jwt filter must be before UsernamePasswordAuthenticationFilter
				// server will check the jwt token first, then use username&password second
				// all request need to be authenticated, if jwt filter hits, username&password filter will not be executed
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authenticationManager(authenticationManager)
				.httpBasic(Customizer.withDefaults())
				// the server never creates a session
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling((exception) -> exception
						.authenticationEntryPoint(customAuthenticationEntryPoint)
						.accessDeniedHandler(customAccessDeniedHandler)
				);
		// most time BearerTokenAuthenticationEntryPoint and BearerTokenAccessDeniedHandler are enough
//				.exceptionHandling((exception) -> exception
//						.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//						.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//				);

		return http.build();
	}

}
