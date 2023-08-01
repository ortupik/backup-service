package com.chris.security.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.context.annotation.Bean;


@Configuration
public class SecurityConfig {
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
		http.authorizeExchange().pathMatchers("/backup/**").hasAuthority("SCOPE_resource.read").anyExchange()
				.authenticated().and().oauth2ResourceServer().jwt();
		return http.build();
	}
}