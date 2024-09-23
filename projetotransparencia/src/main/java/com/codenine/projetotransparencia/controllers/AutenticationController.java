package com.codenine.projetotransparencia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var adminPassword = new  UsernamePasswordAuthenticationToken(data.login(), data.password());
		var auth = this.authenticationManager.authenticate(adminPassword);
		
		var token = tokenService.generateToken((AdministradorV1) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
}
