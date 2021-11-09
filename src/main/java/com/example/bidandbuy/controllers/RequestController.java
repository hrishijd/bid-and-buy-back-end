package com.example.bidandbuy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.bidandbuy.configurations.JwtUtil;
import com.example.bidandbuy.configurations.MyUserDetailsService;
import com.example.bidandbuy.models.Users;
import com.example.bidandbuy.pojo.AuthenticationRequest;
import com.example.bidandbuy.pojo.AuthenticationResponse;
import com.example.bidandbuy.pojo.JwtToken;
import com.example.bidandbuy.repositories.UsersRepository;

@RestController
public class RequestController{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MyUserDetailsService uds;
	@Autowired
	private UsersRepository usersRepo;
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<Object> getToken(@RequestBody AuthenticationRequest ar)
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(ar.getUsername(),ar.getPassword()));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new BadCredentialsException("wrong username or password").getClass());
		}
		final UserDetails ud=uds.loadUserByUsername(ar.getUsername());
		final String jwt= jwtUtil.generateToken(ud);
		final Users user=usersRepo.findByUsername(ar.getUsername());
		return ResponseEntity.ok(new AuthenticationResponse(user,new JwtToken(jwt)));
	}
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<Object> createUser(@RequestBody Users user)
	{
		try {
			usersRepo.save(user);
			return getToken(new AuthenticationRequest(user.getUsername(),user.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.ok(e.getClass());
		}
		
	}
}
