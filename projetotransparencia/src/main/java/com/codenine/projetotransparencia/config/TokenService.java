package com.codenine.projetotransparencia.config;

import java.time.Instant;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.codenine.projetotransparencia.entities.AdministradorV1;




@Service
public class TokenService {
	//preciso arrumar aqui, esta com erro de injeção, onde puxa do application. properties
	//@Value("${api.security.token.secret}")
	@Value("my-secret-key")
	private String secret;
	public String generateToken(AdministradorV1 administradorV1) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(administradorV1.getLogin())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		
	}catch(JWTCreationException exception) {
		throw new RuntimeException("Erro ao gerar token", exception);
		
		}
	}
	
	public String ValidateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		
	}catch(JWTVerificationException exception) {
		return "";
		}
	}
		
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
