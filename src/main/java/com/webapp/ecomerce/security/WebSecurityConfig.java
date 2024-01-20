package com.webapp.ecomerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
	@Autowired
	JWTRequestFilter jwtRequestFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    //TODO: Proper authentication.
	    http.csrf().disable().cors().disable();
	    http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
	    http.authorizeHttpRequests().antMatchers("/product", "/auth/register", "/auth/login", "/auth/verify", "/twillio/sendgrid").permitAll()
	    .anyRequest().authenticated();
	    return http.build();
	  }

}
