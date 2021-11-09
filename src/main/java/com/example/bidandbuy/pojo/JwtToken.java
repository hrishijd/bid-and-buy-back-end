package com.example.bidandbuy.pojo;

import lombok.Getter;
@Getter
public class JwtToken {
	private final String jwtToken;

	public JwtToken(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
}
