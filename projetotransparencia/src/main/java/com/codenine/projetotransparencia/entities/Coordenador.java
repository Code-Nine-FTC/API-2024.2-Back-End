package com.codenine.projetotransparencia.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coordenador_id")
    private Long coordenadorId;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String coordenadorNome;

    @OneToMany(mappedBy = "coordenador", cascade = CascadeType.ALL)
    private List<Projeto> projetos;

	public Long getCoordenadorId() {
		return coordenadorId;
	}

	public void setCoordenadorId(Long coordenadorId) {
		this.coordenadorId = coordenadorId;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCoordenadorNome() {
		return coordenadorNome;
	}

	public void setCoordenadorNome(String coordenadorNome) {
		this.coordenadorNome = coordenadorNome;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}
    
}
