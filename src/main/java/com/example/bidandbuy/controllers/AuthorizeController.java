package com.example.bidandbuy.controllers;

import com.example.bidandbuy.service.GenerateFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.bidandbuy.configurations.JwtUtil;
import com.example.bidandbuy.configurations.MyUserDetailsService;
import com.example.bidandbuy.models.Users;
import com.example.bidandbuy.pojo.AuthenticationRequest;
import com.example.bidandbuy.pojo.AuthenticationResponse;
import com.example.bidandbuy.pojo.JwtToken;
import com.example.bidandbuy.repositories.UsersRepository;

@RestController
@CrossOrigin
public class AuthorizeController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MyUserDetailsService uds;
	@Autowired
	private UsersRepository usersRepo;
	@Autowired
	private GenerateFields generateFields;
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<Object> getToken(@RequestBody AuthenticationRequest ar)
	{
		try{
			return generateFields.getToken(ar);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getClass());
		}
	}
	@RequestMapping(method = RequestMethod.POST, value = "/signup")
	public ResponseEntity<Object> createUser(@RequestBody Users user)
	{
		try {
			return generateFields.instantiateUsers(user);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getClass());
		}
	}
}
