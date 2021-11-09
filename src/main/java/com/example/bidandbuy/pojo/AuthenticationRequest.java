package com.example.bidandbuy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
	private String username;
	private String password;
}
