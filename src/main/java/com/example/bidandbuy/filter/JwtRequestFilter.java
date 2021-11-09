package com.example.bidandbuy.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bidandbuy.configurations.JwtUtil;
import com.example.bidandbuy.configurations.MyUserDetailsService;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService uds;
	@Autowired
	private JwtUtil jwtUtil;
	private String username;
	public String getUsername() {
		return username;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		username=null;
		String jwt=null;
		System.out.println("Hello");
		if(authHeader!=null && authHeader.startsWith("Bearer "))
		{
			jwt=authHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=uds.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails))
			{
				UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upat);
			}
		}
		filterChain.doFilter(request, response);
	}

}
