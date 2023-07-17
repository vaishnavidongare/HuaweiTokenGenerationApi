package com.huawei.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {
	private final WebClient webClient;
	private final String clientId;
	private final String clientSecret;
	
	public TokenService(
			WebClient.Builder webClientBuilder,@Value("${huawei.api.clientId}") String clientId,
			@Value("${huawei.api.clientSecret}") String clientSecret) {
				this.clientId = clientId;
				this.clientSecret = clientSecret;
				this.webClient = webClientBuilder.baseUrl("https://huawei-gems.free.beeceptor.com/oauth2/v2/token").build();
				
		
	}
	public String generateToken() {
		String requestBody = String.format("client_Id=%s&client_secret=%s&grant_type=client_credentials",clientId,clientSecret);
		String tokenResponse = webClient.post().uri("/token").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE).bodyValue(requestBody).retrieve().bodyToMono(String.class).block();
		System.out.println(tokenResponse);
		String token = extractTokenFromResponse(tokenResponse);
		return token;
	}
	private String extractTokenFromResponse(String tokenResponse) {
		// TODO Auto-generated method stub
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode responseJson = objectMapper.readTree(tokenResponse);
			String token = responseJson.get("access_token").asText();
			return token;
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Failed to extract token from response:"+e.getMessage(),e);
		}
	}
	
}
