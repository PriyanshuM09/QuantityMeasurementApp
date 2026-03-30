package com.app.quantitymeasurementapp.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.quantitymeasurementapp.service.CustomUserDetailsService;
import com.app.quantitymeasurementapp.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter{
	
	private JwtService jwtService;
    private CustomUserDetailsService userDetailsService;
    
     /*
      * Constructor injection
      */

	public JwtFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		try {
			if(authHeader!=null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				String email = jwtService.extractEmail(token);
			
				if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
					var userDetails = userDetailsService.loadUserByEmail(email);
					if(jwtService.validateToken(token, email)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}
			}
		}
		catch(Exception e) {
			log.warn("Unauthorized attempt: {}", e.getMessage());
		}
		filterChain.doFilter(request, response);
	}	
}