package com.codenine.projetotransparencia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.codenine.projetotransparencia.config.TokenService;
import com.codenine.projetotransparencia.entities.AdministradorV1;
import com.codenine.projetotransparencia.entities.AuthenticationDTO;
import com.codenine.projetotransparencia.entities.LoginResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AutenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenService tokenService;
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
		try {
			var adminPassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
			var auth = this.authenticationManager.authenticate(adminPassword);

			var token = tokenService.generateToken((AdministradorV1) auth.getPrincipal());
			return ResponseEntity.ok(new LoginResponseDTO(token));
		} catch (AuthenticationException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
		}
	}
}
