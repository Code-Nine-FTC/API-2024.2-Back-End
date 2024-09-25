package com.codenine.projetotransparencia.entities;

public enum AdminRole {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private String role;
	
	AdminRole(String role){
		this.role = role;
		
	}
	public String getRole() {
		return role ;
	}
}
