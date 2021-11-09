package com.example.bidandbuy.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bidandbuy.models.Users;
import com.example.bidandbuy.repositories.UsersRepository;
@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UsersRepository usersRepository;
	private String username;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users users=usersRepository.findByUsername(username);
		if(users==null)
		{
			throw new UsernameNotFoundException("User404");
		}
		username=users.getUsername();
		return new UserDetail(users);
	}

}
