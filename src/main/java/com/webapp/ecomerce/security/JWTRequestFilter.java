package com.webapp.ecomerce.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.webapp.ecomerce.dao.LocalUserDao;
import com.webapp.ecomerce.model.LocalUser;
import com.webapp.ecomerce.service.JWTService;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	JWTService jwtService;
	
	@Autowired
	LocalUserDao localUserDao;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenHeader = request.getHeader("Authorization");
		
		if(tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			String token = tokenHeader.substring(7);
			try {
				String username = jwtService.getUsername(token);
				Optional<LocalUser> opUser = localUserDao.findByUsernameIgnoreCase(username);
				
				if(opUser.isPresent()) {
					
					LocalUser user = opUser.get();
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null, new ArrayList());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}catch (JWTDecodeException ex) {
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
