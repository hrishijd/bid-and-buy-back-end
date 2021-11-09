package com.example.bidandbuy.pojo;



import com.example.bidandbuy.models.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
	private Users user;
	private JwtToken jwtToken;
}
