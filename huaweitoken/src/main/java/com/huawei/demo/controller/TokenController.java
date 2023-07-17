package com.huawei.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.demo.service.TokenService;

@RestController
@RequestMapping("/api")
public class TokenController {
	private final TokenService tokenService;
	
	@Autowired
	public TokenController(TokenService tokenService) {
		this.tokenService=tokenService;
	}
	
	@GetMapping("/token")
	public String getToken(){
		String token = tokenService.generateToken();
		return token;
	}
}
